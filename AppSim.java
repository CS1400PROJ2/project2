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

    public void powerControlAttempt1() {

        ArrayList<SmartAppliance> smartAppliances = new ArrayList<SmartAppliance>();

        for (int i = 0; i < locations.size(); ++i) {
            Location location = locations.get(i);
            ArrayList<SmartAppliance> smartApps = location.getSmartAppliances();
            for (int j = 0; j < smartApps.size(); ++j) {
                smartAppliances.add(smartApps.get(i));
            }
        }
        int currpower = 0;
        Collections.sort(smartAppliances);
        boolean isOk = currpower < allowedWattage;
        for (int i = 0; i < smartAppliances.size(); i++) {
            if (isOk) {
                break;
            } else {
                SmartAppliance currApp = smartAppliances.get(i);
                if (currApp.getState() == "ON") {
                    currApp.setState("LOW");
                    currpower = (int) (currpower - currApp.getConsumption()
                            + currApp.getConsumption() * currApp.getPowerReduction());
                    isOk = currpower < allowedWattage;
                } else
                    continue;
            }
        }
    }

    public void powerControlAttempt2() {
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
