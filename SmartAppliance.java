public class SmartAppliance extends  Appliance{
    protected int locationID;
    protected String appName;
    protected int consumption;
    protected double probOn;
    private String state;
    private boolean isSmart;
    private double powerReduction;
    private int uniqueID;
    public SmartAppliance(int locationID, String appName, int consumption, double probOn, boolean smart,
                     double powerReduction, int uniqueID) {
        super(locationID, appName, consumption, probOn, smart, powerReduction, uniqueID);
    }
}
