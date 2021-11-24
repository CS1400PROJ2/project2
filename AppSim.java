import java.util.ArrayList;
import java.util.Collections;

public class AppSim {
    private int allowedWattage;

    private ArrayList<Location> locations;

    private int affectedSmart = 0;

    private int affectedReg = 0;

    private int affectedLoc = 0;

    public AppSim() {
    }

    public ArrayList<Location> getLocations() {
        return locations;
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

    public void printSummary() {
        System.out.println("Total number of affected location: " + affectedLoc);
        System.out.println("Total number of affected smart appliances: " + affectedSmart);
        System.out.println("Total number of affected regular appliances: " + affectedReg);
    }

    public void powerControlStep1() {
        // Create an ArrayList of smart appliances.
        ArrayList<SmartAppliance> allSmartApps = new ArrayList<SmartAppliance>();
        // for each location in the array of locations, get all the smart appliances
        // and put it into allSmartApps
        int currpower = 0;
        for (int i = 0; i < locations.size(); ++i) {
            Location location = locations.get(i);
            ArrayList<SmartAppliance> smartApps = location.getSmartAppliances();
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
        Collections.sort(allSmartApps);
        // check to make sure the current power > allowed wattage
        boolean isOk = currpower < allowedWattage; // false for now
        for (int i = 0; i < allSmartApps.size(); i++) {
            if (isOk) {
                break;
            } else {
                SmartAppliance currApp = allSmartApps.get(i);
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

    public void step() {

    }
}
