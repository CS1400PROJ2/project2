import java.util.ArrayList;
//hello, I just pushed this :D

public class Location
{

    private ArrayList<Appliance> appliances = new ArrayList<Appliance>();
    private int locationID;

    public Location(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    int getTotalWattage() {
        int totalWattage = 0;
        for (int i = 0; i < appliances.size(); ++i) {
            Appliance appliance = appliances.get(i);
            if (appliance.getState() == "ON") {
                totalWattage += appliance.getConsumption();
            } else if (appliance.getState() == "LOW") {
                SmartAppliance smartAppliance = (SmartAppliance) appliance;
                totalWattage += smartAppliance.getLowConsumption();
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

    public void setApplianceOn(int index)
    {
        Appliance appliance = appliances.get(index);
        appliance.setState("ON");
        appliances.set(index, appliance);
    }

    public void setApplianceOff(int index)
    {
        Appliance appliance = appliances.get(index);
        appliance.setState("OFF");
        appliances.set(index, appliance);
    }

    public void setApplianceLow(int index)
    {
        Appliance appliance = appliances.get(index);
        appliance.setState("LOW");
        appliances.set(index, appliance);
    }
}
