import java.util.ArrayList;
import java.util.Comparator;

import javax.sound.sampled.SourceDataLine;

public class Appliance {
    protected int locationID;
    protected String appName;
    protected int consumption;
    protected double probOn;
    private String state;
    private boolean isSmart;
    private double powerReduction;
    private int uniqueID;

    public static Comparator<Appliance> ByLocation = new Comparator<Appliance>() {
        public int compare(Appliance o1, Appliance o2) {
            int con1 = o1.getLocationID();
            int con2 = o2.getLocationID();
            return con1 - con2;
        }
    };



    public Appliance(int locationID, String appName, int consumption, double probOn, boolean smart,
            double powerReduction, int uniqueID) {
        this.locationID = locationID;
        this.appName = appName;
        this.consumption = consumption;
        this.probOn = probOn;
        this.isSmart = smart;
        this.powerReduction = powerReduction;
        this.uniqueID = uniqueID;
    }



    public int getLocationID() {
        return locationID;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void  setUniqueID(int uniqueID){ this.uniqueID = uniqueID; }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public double getProbOn() {
        return probOn;
    }

    public void setProbOn(double probOn) {
        this.probOn = probOn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSmart(Boolean isSmart) {
        this.isSmart = isSmart;
    }

    public boolean getSmart() {
        return (this.powerReduction != 0);
    }

    public void printAppliance() {
        System.out.println(
                locationID + "," + appName + "," + consumption + "," + probOn + "," + isSmart + "," + powerReduction + "," + uniqueID);
    }

    public String applianceToString() {
        return locationID + "," + appName + "," + consumption + "," + probOn + "," + isSmart + "," + powerReduction+ "," + uniqueID;
    }

    public int getLowConsumption() {
        return (int) (this.getConsumption() * this.powerReduction);
    }

    public double getPowerReduction() {
        return powerReduction;
    }

    public void setPowerReduction(double powerReduction) {
        this.powerReduction = powerReduction;
    }

}
