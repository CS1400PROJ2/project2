public class SmartAppliance extends Appliance {
    private double powerReduction;

    public SmartAppliance(int locationID, String appName, int consumption, double probOn, double powerReduction) {
        super(locationID, appName, consumption, probOn);
        this.powerReduction = powerReduction;
    }

    public double getPowerReduction() {
        return powerReduction;
    }

    public void setPowerReduction(double powerReduction) {
        this.powerReduction = powerReduction;
    }

}
