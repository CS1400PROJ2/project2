public class SmartAppliance extends Appliance
{
    private int lowPower;

    public SmartAppliance(int locationID, String appName, int onPower, double probOn, int lowPower) 
    {
        super(locationID, appName, onPower, probOn);
        this.lowPower = lowPower;
    }

    public int getLowPower()
    {
        return lowPower;
    }

    public void setLowPower(int lowPower)
    {
        this.lowPower = lowPower;
    }
}
