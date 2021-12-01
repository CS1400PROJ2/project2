import java.util.Comparator;

public class SmartAppliance extends Appliance implements Comparable<SmartAppliance>{
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
    public Comparator<SmartAppliance> ByConsumption = new Comparator<SmartAppliance>() {
        public int compare(SmartAppliance o1, SmartAppliance o2) {
            int con1 = o1.getConsumption();
            int con2 = o2.getConsumption();
            return con2 - con1;
        }
    };

    @Override
    public int compareTo(SmartAppliance comparestu) {
        int compareconsumption=((comparestu).getConsumption());
        /* For descending order*/
        return compareconsumption-this.getConsumption();
    }
}
