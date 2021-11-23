import java.util.ArrayList;
//hello, I just pushed this :D

public class Location implements Comparable<Location> {

    private ArrayList<Appliance> appliances;
    private int locationID;

    public Location(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getCurrentConsumption() {
        int consumption = 0;
        for (int i = 0; i < appliances.size(); i++) {
            Appliance currApp = appliances.get(i);
            if (currApp instanceof SmartAppliance) {
                if (currApp.getState() == "LOW") {
                    consumption = consumption
                            + (int) ((SmartAppliance) currApp).getPowerReduction() * currApp.getConsumption();
                } else if (currApp.getState() == "ON") {
                    consumption = consumption + currApp.getConsumption();
                } else
                    continue;
            } else {
                if (currApp.getState() == "ON") {
                    consumption = consumption + currApp.getConsumption();
                } else
                    continue;
            }
        }
        return consumption;
    }

    int getTotalWattage() {
        int totalWattage = 0;
        for (int i = 0; i < appliances.size(); ++i) {
            Appliance appliance = appliances.get(i);
            if (appliance.getState() == "ON") {
                totalWattage += appliance.getConsumption();
            } else if (appliance.getState() == "LOW") {
                SmartAppliance smartAppliance = (SmartAppliance) appliance;
                totalWattage += smartAppliance.getPowerReduction();
            }
        }

        return totalWattage;
    }

    public void brownOut() {
        for (int i = 0; i < appliances.size(); ++i) {
            Appliance appliance = appliances.get(i);
            appliance.setState("OFF");
        }
    }

    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    public ArrayList<SmartAppliance> getSmartAppliances() {
        ArrayList<SmartAppliance> smartApps = new ArrayList<SmartAppliance>();
        for (int i = 0; i < appliances.size(); i++) {
            Appliance currApp = appliances.get(i);
            if (currApp instanceof SmartAppliance) {
                smartApps.add((SmartAppliance) currApp);
            }
        }
        return smartApps;
    }

    public ArrayList<Appliance> getAppliances() {
        return appliances;
    }

    @Override
    public int compareTo(Location o) {
        int compareNumAppliances = o.getAppliances().size();
        // descending order
        return compareNumAppliances - this.getAppliances().size();
    }

}
