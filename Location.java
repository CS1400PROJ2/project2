import java.util.ArrayList;

public class Location {

    private ArrayList<Appliance> appliances;
    private int locationID;

    public Location(int locationID)
    {
        this.locationID = locationID;
    }

    public int getLocationID()
    {
        return locationID;
    }

    int getTotalWattage(){
        int totalWattage = 0;
        for (int i = 0; i < appliances.size(); ++i)
        {
            Appliance appliance = appliances.get(i);
            if (appliance.getState() == "ON")
            {
                totalWattage += appliance.getOnPower();
            }
            else if (appliance.getState() == "LOW")
            {
                SmartAppliance smartAppliance = (SmartAppliance)appliance;
                totalWattage += smartAppliance.getLowPower();
            }
        }

        return totalWattage;
    }

    public void brownOut()
    {
        for (int i = 0; i < appliances.size(); ++i)
        {
            Appliance appliance = appliances.get(i);
            appliance.setState("OFF");
        }
    }

    public void addAppliance(Appliance appliance)
    {
        appliances.add(appliance);
    }
}
