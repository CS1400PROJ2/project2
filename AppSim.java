import java.util.ArrayList;

public class AppSim 
{
    private int allowedWattage;

    private ArrayList<Appliance> appliances;

    private ArrayList<Location> locations;

    private int affectedSmart = 0;

    private int affectedReg = 0;

    private int affectedLoc = 0;
    
    public AppSim(){}

    public ArrayList<Location> getLocations()
    {
        return locations;
    }

    public int getAllowedWattage()
    {
        return allowedWattage;
    }

    public void setAllowedWattage (int allowedWattage)
    {
        this.allowedWattage = allowedWattage;
    }

    public void addAppliance(Appliance appliance)
    {
        appliances.add(appliance);
    }

    public void addLocation(Location location)
    {
        locations.add(location);
    }

    public void printSummary()
    {
        System.out.println("Total number of affected location: " + affectedLoc);
        System.out.println("Total number of affected smart appliances: " + affectedSmart);
        System.out.println("Total number of affected regular appliances: " + affectedReg);
    }

    public void step()
    {
        
    }
}