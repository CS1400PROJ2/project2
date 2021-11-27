import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.IOException;

public class AppSim {
    private int allowedWattage;
    private ArrayList<Location> locations = new ArrayList<Location>();

    ArrayList<Integer> smartToLow = new ArrayList<Integer>();
    ArrayList<Integer> brownOutLocations = new ArrayList<Integer>();

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locs) {
        locations = locs;
    }

    public int getAllowedWattage() {
        return allowedWattage;
    }

    public void setAllowedWattage(int allowedWattage) {
        this.allowedWattage = allowedWattage;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void powerControlStep1() {
        // Create an ArrayList of smart appliances.
        ArrayList<Appliance> allSmartApps = new ArrayList<Appliance>();
        // for each location in the array of locations, get all the smart appliances
        // and put it into allSmartApps
        int currpower = 0;
        for (int i = 0; i < locations.size(); ++i) {
            Location location = locations.get(i);
            ArrayList<Appliance> smartApps = location.getSmartAppliances();
            for (int j = 0; j < smartApps.size(); ++j) {
                allSmartApps.add(smartApps.get(i));
                // calculate currpower by adding the consumption of all the on/low appliances
                if (smartApps.get(i).getState() == "ON") {
                    currpower = currpower + smartApps.get(i).getConsumption();
                } else if (smartApps.get(i).getState() == "LOW") {
                    currpower = currpower + (smartApps.get(i).getLowConsumption());
                } else
                    continue;
            }
        }
        // sort the smart appliances in decreasing order of consumption
        allSmartApps.sort(Comparator.comparing(Appliance::getConsumption));
        ;
        // check to make sure the current power > allowed wattage
        boolean isOk = currpower < allowedWattage; // false for now
        for (int i = 0; i < allSmartApps.size(); i++) {
            if (isOk) {
                break;
            } else {
                Appliance currApp = allSmartApps.get(i);
                if (currApp.getState() == "ON") {
                    currApp.setState("LOW");
                    currpower = currpower - currApp.getConsumption() + currApp.getLowConsumption();
                    isOk = currpower < allowedWattage;
                } else
                    continue;
            }
        }
    }

    public void powerControlStep2() {
        Collections.sort(locations);
        int currConsumption = 0;
        for (int i = 0; i < locations.size(); i++) {
            Location currLocation = locations.get(i);
            currConsumption = currConsumption + currLocation.getCurrentConsumption();
        }
        boolean isOk = currConsumption < allowedWattage;
        for (int i = 0; i < locations.size(); i++) {
            Location currLocation = locations.get(i);
            if (isOk) {
                break;
            } else {
                currLocation.brownOut();
                currConsumption = currConsumption - currLocation.getCurrentConsumption();
            }
        }

    }

    public void saveSummary() {
        try {
            File myObj = new File("summary.txt");
            myObj.createNewFile();

            FileWriter fw = new FileWriter("summary.txt");

            for (int i = 0; i < smartToLow.size(); i++) {
                fw.write("Step number " + (i + 1));
                fw.write('\n');
                fw.write("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
                fw.write('\n');
                fw.write("Number of browned out locations during this step: " + brownOutLocations.get(i));
                fw.write('\n');
            }

            fw.close();
        } catch (IOException error) {
        }
    }

    public void simulationLoop(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
            System.out.println("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
            System.out.println("Number of browned out locations during this step: " + brownOutLocations.get(i));
        }
        saveSummary();
    }

    public void step() {
        int totalWattage = 0;

        int smartToLowCount = 0;
        int brownOutLocationsCount = 0;

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            totalWattage = totalWattage + location.getTotalWattage();
        }

        if (totalWattage > allowedWattage) {
            System.out.println("Total number of locations affected: ");

        }

        for (int i = 0; i < locations.size(); i++) {
            double probOn = 0;
            Location location = locations.get(i);
            ArrayList<Appliance> appliances = location.getAppliances();

            for (int j = 0; j < appliances.size(); j++) {
                Appliance appliance = appliances.get(j);
                probOn = appliance.getProbOn();

                double n = Math.random();
                if (n > probOn) {
                    if (appliance.getState() != "OFF") {
                        location.setApplianceOff(j);
                        locations.set(i, location);
                    }
                } else {
                    if (appliance.getState() == "OFF") {
                        location.setApplianceOn(j);
                        locations.set(i, location);
                    }
                }
            }
        }

        smartToLow.add(smartToLowCount);
        brownOutLocations.add(brownOutLocationsCount);
    }
}
