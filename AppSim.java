import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;

public class AppSim {
    private int allowedWattage;
    private ArrayList<Location> locations = new ArrayList<Location>();

    ArrayList<Integer> smartToLow = new ArrayList<Integer>();
    ArrayList<Integer> brownOutLocations = new ArrayList<Integer>();
    ArrayList<Integer> regAffected = new ArrayList<Integer>();
    ArrayList<Integer> smartAffected = new ArrayList<Integer>();
    ArrayList<Integer> locAffected = new ArrayList<Integer>();

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

    public void setLocation(int index, Location location)
    {
        locations.set(index, location);
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
                fw.write("Number of Smart Appliances affected: " + smartAffected.get(i));
                fw.write('\n');
                fw.write("Number of Regular Appliances affected: " + regAffected.get(i));
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
            System.out.println("Number of Smart Appliances affected: " + smartAffected.get(i));
            System.out.println("Number of Regular Appliances affected: " + regAffected.get(i));
        }
        saveSummary();
    }

    public void step()
    {
        int totalWattage = 0;

        int smartToLowCount = 0;
        int brownOutLocationsCount = 0;
        int smartAffectedCount = 0;
        int regAffectedCount = 0;

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
                        ArrayList<SmartAppliance> smartApps = location.getSmartAppliances();
                        for (int j = 0; j < smartApps.size(); j++)
                        {
                            SmartAppliance smartAppliance = smartApps.get(j);
                            if (smartAppliance.getUniqueID() == uniqueID)
                            {
                                smartToLowCount += 1;
                                smartAffectedCount += 1;
                                location.setSmartApplianceLow(j);
                                setLocation(i, location);
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
                    smartAffectedCount += location.getSmartOnCount();
                    regAffectedCount += location.getRegOnCount();
                    brownOutLocationsCount += 1;
                    totalWattage -= location.getTotalWattage();
                    location.brownOut();
                    setLocation(i, location);
                }
            }
        }

        for (int i = 0; i < locations.size(); i++)
        {
            double probOn = 0;
            Location location = locations.get(i);
            ArrayList<Appliance> regAppliances = location.getRegAppliances();
            ArrayList<SmartAppliance> smartAppliances = location.getSmartAppliances();

            for (int j = 0; j < regAppliances.size(); j++)
            {
                Appliance regAppliance = regAppliances.get(j);
                probOn = regAppliance.getProbOn();

                double n = Math.random();
                if (n > probOn){
                    if (regAppliance.getState() != "OFF")
                    {
                        location.setRegApplianceOff(j);
                    }
                }
                else {
                    if (regAppliance.getState() == "OFF")
                    {
                        location.setRegApplianceOn(j);
                    }
                }
            }


            for (int j = 0; j < smartAppliances.size(); j++)
            {
                Appliance smartAppliance = smartAppliances.get(j);
                probOn = smartAppliance.getProbOn();

                double n = Math.random();
                if (n > probOn){
                    if (smartAppliance.getState() != "OFF")
                    {
                        location.setSmartApplianceOff(j);
                    }
                }
                else {
                    if (smartAppliance.getState() == "OFF")
                    {
                        location.setSmartApplianceOn(j);
                    }
                }
            }
            setLocation(i, location);
        }

        smartToLow.add(smartToLowCount);
        brownOutLocations.add(brownOutLocationsCount);
        smartAffected.add(smartAffectedCount);
        regAffected.add(regAffectedCount);
    }
}
