public class SmartAppliance extends Appliance {
    private boolean isSmart = true;
    private int lowConsumption;

    public SmartAppliance(int uniqueID, int locationID, String appName, int consumption, double probOn, boolean isSmart,int lowConsumption) {
        super(uniqueID, locationID, appName, consumption, probOn, isSmart);
        this.lowConsumption = lowConsumption;
    }

    public int getLowConsumption() {
        return lowConsumption;
    }

    public void setLowConsumption(int lowConsumption) {
        this.lowConsumption = lowConsumption;
    }

    public boolean getIsSmart() {
        return isSmart;
    }
}
