public class Appliance
{
    private int locationID;
    private String appName;
    private int onPower;
    private double probOn;
    private String state = "ON";

    public Appliance(int locationID, String appName, int onPower, double probOn)
    {
        this.locationID = locationID;
        this.appName = appName;
        this.onPower = onPower;
        this.probOn = probOn;
    }

    public int getLocationID()
    {
        return locationID;
    }

    public void setLocationID(int locationID)
    {
        this.locationID = locationID;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public int getOnPower()
    {
        return onPower;
    }

    public void setOnPower(int onPower)
    {
        this.onPower = onPower;
    }

    public double getProbOn()
    {
        return probOn;
    }

    public void setProbOn(double probOn)
    {
        this.probOn = probOn;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
