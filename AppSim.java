import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;

public class AppSim {
    private int allowedWattage;
    private ArrayList<Location> locations = new ArrayList<Location>();

    ArrayList<Integer> smartToLow = new ArrayList<Integer>();
    ArrayList<Integer> brownOutLocations = new ArrayList<Integer>();

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

    public void saveSummary()
    {
        try {
            File myObj = new File("summary.txt");
            myObj.createNewFile();

            FileWriter fw = new FileWriter("summary.txt", false);

            for (int i = 0; i < smartToLow.size(); i++)
            {
                fw.write("Step number " + (i+1));
                fw.write('\n');
                fw.write("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
                fw.write('\n');
                fw.write("Number of browned out locations during this step: " + brownOutLocations.get(i));
                fw.write('\n');
            }

            fw.close();            
          } 
          catch (IOException error) {}
    }

    public void simulationLoop(int steps) 
    {
        for (int i = 0; i < steps; i++)
        {
            step();
            System.out.println("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
            System.out.println("Number of browned out locations during this step: " + brownOutLocations.get(i));
        }
        saveSummary();
    }

    public void step()
    {
        int totalWattage = 0;

        int smartToLowCount = 0;
        int brownOutLocationsCount = 0;

        for (int i = 0; i < locations.size(); i++)
        {
            Location location = locations.get(i);
            totalWattage = totalWattage + location.getTotalWattage();
        }

        if (totalWattage > allowedWattage)
        {
            ArrayList<SmartAppliance> smartAppliances = new ArrayList<SmartAppliance>();

            for (int i = 0; i < locations.size(); i++)
            {
                Location location = locations.get(i);
                smartAppliances.addAll(location.getSmartAppliances());
            }


            while (totalWattage > allowedWattage)
            {
                int mostWattage = -1;
                int uniqueID = -1;
                for (int i = 0; i < smartAppliances.size(); i++)
                {
                    SmartAppliance smartAppliance = smartAppliances.get(i);
                    if (smartAppliance.getState() == "ON" && smartAppliance.getConsumption() > mostWattage)
                    {
                        mostWattage = smartAppliance.getConsumption();
                        uniqueID = smartAppliance.getUniqueID();
                    }
                }

                if (uniqueID == -1)
                {
                    break;
                }
                else
                {
                    for (int i = 0; i < locations.size(); i++)
                    {
                        Location location = locations.get(i);
                        ArrayList<Appliance> appliances = location.getAppliances();
                        for (int j = 0; j < appliances.size(); j++)
                        {
                            SmartAppliance smartAppliance = (SmartAppliance)appliances.get(j);
                            if (smartAppliance.getUniqueID() == uniqueID)
                            {
                                smartToLowCount += 1;
                                location.setApplianceLow(j);
                                locations.set(i, location);
                                totalWattage -= (smartAppliance.getConsumption() - smartAppliance.getLowConsumption());
                                break;
                            }
                        }
                    }
                }
            }
        }

      
        while (totalWattage > allowedWattage)
        {
            int mostWattage = -1;
            int locationID = -1;

            for (int i = 0; i < locations.size(); i++)
            {
                Location location = locations.get(i);
                if (location.getTotalWattage() > mostWattage)
                {
                    mostWattage = location.getTotalWattage();
                    locationID = location.getLocationID();
                }
            }

            for (int i = 0; i < locations.size(); i++)
            {
                Location location = locations.get(i);
                if (location.getLocationID() == locationID)
                {
                    brownOutLocationsCount += 1;
                    totalWattage -= location.getTotalWattage();
                    location.brownOut();
                    locations.set(i, location);
                }
            }
        }

        for (int i = 0; i < locations.size(); i++)
        {
            double probOn = 0;
            Location location = locations.get(i);
            ArrayList<Appliance> appliances = location.getAppliances();

            for (int j = 0; j < appliances.size(); j++)
            {
                Appliance appliance = appliances.get(j);
                probOn = appliance.getProbOn();

                double n = Math.random();
                if (n > probOn){
                    if (appliance.getState() != "OFF")
                    {
                        location.setApplianceOff(j);
                        locations.set(i, location);
                    }
                }
                else {
                    if (appliance.getState() == "OFF")
                    {
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
