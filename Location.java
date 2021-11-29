import java.util.ArrayList;
//hello, I just pushed this :D

public class Location implements Comparable<Location> {

    private ArrayList<Appliance> appliances = new ArrayList<Appliance>();
    private int locationID;

    public Location(int locationID) {
        this.locationID = locationID;
    }
    public Location(int locationID, ArrayList<Appliance> appliances) {
        this.locationID = locationID;
        this.appliances = appliances;
    }

    public void setAppliances(ArrayList<Appliance> apps){
        this.appliances = apps;

    }

    public int getLocationID() {
        return locationID;
    }

    public int getCurrentConsumption() {
        int consumption = 0;
        for (int i = 0; i < appliances.size(); i++) {
            Appliance currApp = appliances.get(i);
            if (currApp.getSmart()) {
                if (currApp.getState() == "LOW") {
                    consumption = consumption
                            + (int) (currApp).getPowerReduction() * currApp.getConsumption();
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

                totalWattage += appliance.getPowerReduction();
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
            if (appliances.get(i) instanceof SmartAppliance) {
                smartApps.add((SmartAppliance) appliances.get(i));
            }
        }
        return smartApps;
    }

    public ArrayList<Appliance> getAllAppliances() {
        return appliances;
    }

    public void setApplianceOn(int index) {
        Appliance appliance = appliances.get(index);
        appliance.setState("ON");
        appliances.set(index, appliance);
    }

    public void setApplianceOff(int index) {
        Appliance appliance = appliances.get(index);
        appliance.setState("OFF");
        appliances.set(index, appliance);
    }

    public void setApplianceLow(int index) {
        Appliance appliance = appliances.get(index);
        appliance.setState("LOW");
        appliances.set(index, appliance);
    }

    @Override
    public int compareTo(Location o) {
        int compareNumAppliances = o.getAllAppliances().size();
        // descending order
        return compareNumAppliances - this.getAllAppliances().size();
    }

}
