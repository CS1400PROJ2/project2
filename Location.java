import java.util.ArrayList;
//hello, I just pushed this :D

public class Location
{

    private ArrayList<Appliance> regAppliances = new ArrayList<Appliance>();
    private ArrayList<SmartAppliance> smartAppliances = new ArrayList<SmartAppliance>();
    private int locationID;

    public Location(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    int getTotalWattage() {
        int totalWattage = 0;
        for (int i = 0; i < regAppliances.size(); ++i) {
            Appliance appliance = regAppliances.get(i);
            if (appliance.getState() == "ON") {
                totalWattage += appliance.getConsumption();
            } 
        }
        for (int i = 0; i < smartAppliances.size(); ++i) {
            SmartAppliance smartAppliance = smartAppliances.get(i);
            if (smartAppliance.getState() == "ON") {
                totalWattage += smartAppliance.getConsumption();
            } 
            else if (smartAppliance.getState() == "LOW") {
                totalWattage += smartAppliance.getLowConsumption();
            } 
        }
        return totalWattage;
    }

    public void brownOut() {
        for (int i = 0; i < regAppliances.size(); ++i) {
            Appliance regAppliance = regAppliances.get(i);
            regAppliance.setState("OFF");
            regAppliances.set(i, regAppliance);
        }
        for (int i = 0; i < smartAppliances.size(); ++i) {
            SmartAppliance smartAppliance = smartAppliances.get(i);
            smartAppliance.setState("OFF");
            smartAppliances.set(i, smartAppliance);
        }
    }

    public int getSmartOnCount()
    {
        int count = 0;
        for(int i = 0; i < smartAppliances.size(); i++)
        {
            SmartAppliance smartAppliance = smartAppliances.get(i);
            if (!smartAppliance.getState().equals("OFF"))
            {
                count += 1;
            }
        }
        return count;
    }

    public int getRegOnCount()
    {
        int count = 0;
        for(int i = 0; i < regAppliances.size(); i++)
        {
            Appliance regAppliance = regAppliances.get(i);
            if (!regAppliance.getState().equals("OFF"))
            {
                count += 1;
            }
        }
        return count;
    }

    public void addRegAppliance(Appliance regAppliance) {
        regAppliances.add(regAppliance);
    }

    public void addSmartAppliance(SmartAppliance smartAppliance) {
        smartAppliances.add(smartAppliance);
    }

    public ArrayList<SmartAppliance> getSmartAppliances() {
        return smartAppliances;
    }

    public ArrayList<Appliance> getRegAppliances() {
        return regAppliances;
    }

    public void setRegApplianceOn(int index)
    {
        Appliance regAppliance = regAppliances.get(index);
        regAppliance.setState("ON");;
        regAppliances.set(index, regAppliance);
    }

    public void setRegApplianceOff(int index)
    {
        Appliance regAppliance = regAppliances.get(index);
        regAppliance.setState("OFF");
        regAppliances.set(index, regAppliance);
    }

    public void setSmartApplianceOn(int index)
    {
        SmartAppliance smartAppliance = smartAppliances.get(index);
        smartAppliance.setState("ON");;
        smartAppliances.set(index, smartAppliance);
    }

    public void setSmartApplianceOff(int index)
    {
        SmartAppliance smartAppliance = smartAppliances.get(index);
        smartAppliance.setState("OFF");
        smartAppliances.set(index, smartAppliance);
    }

    public void setSmartApplianceLow(int index)
    {
        SmartAppliance smartAppliance = smartAppliances.get(index);
        smartAppliance.setState("LOW");
        smartAppliances.set(index, smartAppliance);
    }
}
