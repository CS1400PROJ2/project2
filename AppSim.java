import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Set;

public class AppSim {
    private int allowedWattage;
    private ArrayList<Location> locations = new ArrayList<Location>();

    ArrayList<Integer> smartToLow = new ArrayList<Integer>();
    ArrayList<Integer> brownOutLocations = new ArrayList<Integer>();
    ArrayList<Integer> regAffected = new ArrayList<Integer>();
    ArrayList<Integer> smartAffected = new ArrayList<Integer>();
    ArrayList<Integer> locAffected = new ArrayList<Integer>();
    ArrayList<Set<Integer>> affectedLocations = new ArrayList<Set<Integer>>();
    ArrayList<String> reportToWrite = new ArrayList<>();

    private int stepNumber;

    public void setStepNumber(int steps){
        this.stepNumber = steps;
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
    public void saveDetailedReport()
    {
        try {
            File myObj = new File("detailedReport.txt");
            myObj.createNewFile();

            FileWriter fw = new FileWriter("detailedReport.txt", false);

            for(String str: reportToWrite) {
                fw.write(str + System.lineSeparator());
            }

            fw.close();
        }
        catch (IOException error) {}
    }

    public void simulationLoop(int steps, ArrayList<Appliance> apps){
        reportToWrite.add("Your simulation was "+steps+" time steps long. Let's look at what happened");
        reportToWrite.add("Total number of appliances: "+ apps.size());
        ArrayList<Location> createdLocs = createLocationList(apps);

        for (int i = 0; i < steps; i++)
        {
            reportToWrite.add("This is what happened on step number "+(i + 1));
            step(createdLocs);
            reportToWrite.add("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
            reportToWrite.add("Number of browned out locations during this step: " + brownOutLocations.get(i));
            System.out.println("Number of Smart Appliances turned to low during this step: " + smartToLow.get(i));
            System.out.println("Number of browned out locations during this step: " + brownOutLocations.get(i));
            if(smartToLow.get(i) == 0){
                System.out.println("We didn't spend hours on a power control algorithm for nothing! Next time, try inputting a smaller allowed wattage");
                reportToWrite.add("Power control was never used during this step");
            }
        }
        saveSummary();
        saveDetailedReport();
    }

public ArrayList<Location> createLocationList(ArrayList<Appliance> apps) {
    ArrayList<Location> locs = new ArrayList<>();
    ArrayList<Integer> intLocs = new ArrayList<>();
    for (int i = 0; i < apps.size(); i++) {
        int currLoc = apps.get(i).getLocationID();
        if (intLocs.contains(currLoc)) {
            continue;
        } else intLocs.add(currLoc);
    }



    for (int i = 0; i < intLocs.size(); i++) {
        ArrayList<Appliance> appsAtLoc = new ArrayList<>();
        int currentLoc = intLocs.get(i);
        for (int j = 0; j < apps.size(); j++) {
            Appliance currapp = apps.get(j);
            if (currapp.getLocationID() == currentLoc){
                appsAtLoc.add(currapp);
            }
        }
        Location loc = new Location(currentLoc, appsAtLoc);
        locs.add(loc);
    }
    return locs;
        }

    public void step(ArrayList<Location> createdLocs)
    {
        int totalWattage = 0;
        int smartToLowCount = 0;
        int brownOutLocationsCount = 0;
        int onBeforeControl = 0;
        int offBeforeControl = 0;
        Location maxAffectedLocation;
        for (int i = 0; i < createdLocs.size(); i++)
        {
            double probOn = 0;
            Location location = createdLocs.get(i);
            ArrayList<Appliance> appliances = location.getAllAppliances();

            for (int j = 0; j < appliances.size(); j++)
            {
                Appliance appliance = appliances.get(j);
                probOn = appliance.getProbOn();

                double n = Math.random();
                if (n > probOn){
                    if (appliance.getState() != "OFF")
                    {
                        location.setApplianceOff(j);
                        createdLocs.set(i, location);
                        offBeforeControl++;
                    }
                }
                else {
                    if (appliance.getState() != "ON")
                    {
                        location.setApplianceOn(j);
                        createdLocs.set(i, location);
                        onBeforeControl++;
                    }
                }
            }
        }
        reportToWrite.add("Appliances turned on before power control: " + onBeforeControl);
        reportToWrite.add("Appliances left off before power control: " + offBeforeControl);

        for (int i = 0; i < createdLocs.size(); i++)
        {
            Location location = createdLocs.get(i);
            totalWattage = totalWattage + location.getTotalWattage();
        }

        if (totalWattage > allowedWattage)
        {
            reportToWrite.add("The total wattage was greater than the allowed wattage.");
            System.out.println("The total wattage of your appliances is "+totalWattage+ " watts, which is greater than your allowed wattage of "+allowedWattage+" watts. \nWe're switching some of your smart appliances to their low power mode to see if it helps.");

            ArrayList<SmartAppliance> smartAppliances = new ArrayList<SmartAppliance>();

            for (int i = 0; i < createdLocs.size(); i++)
            {
                Location location = createdLocs.get(i);
                smartAppliances.addAll(location.getSmartAppliances());
            }

            int stepCounter = 1;
            while (totalWattage > allowedWattage)
            {
                int mostWattage = -1;
                int uniqueID = -1;
                SmartAppliance mostConsuming = null;

                for (int i = 0; i < smartAppliances.size(); i++)
                {
                    SmartAppliance smartAppliance = smartAppliances.get(i);
                    if (smartAppliance.getState() == "ON" && smartAppliance.getConsumption() > mostWattage)
                    {
                        mostWattage = smartAppliance.getConsumption();
                        uniqueID = smartAppliance.getUniqueID();
                        mostConsuming = smartAppliance;
                    }
                }
                if (uniqueID == -1)
                {
                    break;
                }
                else
                {
                    int locationNumber = mostConsuming.getLocationID() - 10000000;
                    Location location1 = createdLocs.get(locationNumber - 1);
                    ArrayList<Appliance> appliances1 = location1.getAllAppliances();
                    int setToLow = -1;
                    for(int i = 0; i < appliances1.size(); i++){
                        if(appliances1.get(i).getUniqueID() == uniqueID){
                            setToLow = i;
                            break;
                        }
                    }
                    smartToLowCount += 1;
                    location1.setApplianceLow(setToLow);
                    reportToWrite.add("The smart appliance "+ mostConsuming.applianceToString() + " was turned to low during this step");
                    totalWattage -= (mostConsuming.getConsumption() - mostConsuming.getLowConsumption());
                    stepCounter++;
                }
            }
        }
        if(totalWattage > allowedWattage){
            reportToWrite.add("After turning all smart appliances to low, the current wattage is " + totalWattage+ " which is greater than your allowed wattage. \nCommencing brownout.");
            reportToWrite.add("Note that the first location mentioned in this step had the most wattage and is therefore the max affected location");
            System.out.println("The current wattage is " + totalWattage+ " which is greater than your allowed wattage. \nCommencing brownout.");
        }
        else if (totalWattage < allowedWattage){
            System.out.println("Yay! That worked!");
            reportToWrite.add("After turning all smart appliances to low, the current wattage is " + totalWattage+ " which is less than your allowed wattage");
            reportToWrite.add("The brown out was never used during this step");
        }

        while (totalWattage > allowedWattage)
        {
            int mostWattage = -1;
            int locationID = -1;

            for (int i = 0; i < createdLocs.size(); i++)
            {
                Location location = createdLocs.get(i);
                if (location.getTotalWattage() > mostWattage)
                {
                    mostWattage = location.getTotalWattage();
                    locationID = location.getLocationID();
                }
            }

            for (int i = 0; i < createdLocs.size(); i++)
            {
                Location location = createdLocs.get(i);
                if (location.getLocationID() == locationID)
                {
                    brownOutLocationsCount += 1;
                    reportToWrite.add("The location " + locationID + " was browned out. It had a wattage of " + location.getTotalWattage() + " watts.");
                    totalWattage -= location.getTotalWattage();

                    location.brownOut();

                    createdLocs.set(i, location);
                }
            }
        }
        reportToWrite.add("The wattage is now "+totalWattage+" watts");
        System.out.println("The wattage is now "+totalWattage+" watts");
        smartToLow.add(smartToLowCount);
        brownOutLocations.add(brownOutLocationsCount);
    }
}
