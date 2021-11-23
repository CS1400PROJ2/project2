public class SmartAppliance extends Appliance {
    private double powerReduction;
    private boolean isSmart = true;

    public SmartAppliance(int locationID, String appName, int consumption, double probOn, boolean isSmart,
            double powerReduction) {
        super(locationID, appName, consumption, probOn, isSmart);
        this.powerReduction = powerReduction;
    }

    public double getPowerReduction() {
        return powerReduction;
    }

    public void setPowerReduction(double powerReduction) {
        this.powerReduction = powerReduction;
    }

    public boolean getIsSmart() {
        return isSmart;
    }

}
