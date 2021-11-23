
public class Appliance implements Comparable<Appliance> {
    private int locationID;
    private String appName;
    private int consumption;
    private double probOn;
    private String state;

    public Appliance(int locationID, String appName, int consumption, double probOn) {
        this.locationID = locationID;
        this.appName = appName;
        this.consumption = consumption;
        this.probOn = probOn;

    }

    public int getLocationID() {
        return locationID;
    }

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

    @Override
    public int compareTo(Appliance comparestu) {
        int compareConsumption = comparestu.getConsumption();
        // descending order
        return compareConsumption - this.getConsumption();

    }
}
