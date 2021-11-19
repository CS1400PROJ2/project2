public class Appliance
{
    private String str;
    private int locationID;
    private String appName;
    private int onPower;
    private double probOn;
    private String appType;
    private int lowPower;
    private String state;

    public Appliance(String str, int locationID, String appName, int onPower, double probOn, String appType, int lowPower)
    {
        this.str = str;
        this.locationID = locationID;
        this.appName = appName;
        this.onPower = onPower;
        this.probOn = probOn;
        this.appName = appType;
        this.lowPower = lowPower;
    }

    public String getStr()
    {
        return str;
    }

    public void setStr(String str)
    {
        this.str = str;
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

    public String getAppType()
    {
        return appType;
    }

    public void setAppType(String appType)
    {
        this.appType = appType;
    }

    public int getLowPower()
    {
        return lowPower;
    }

    public void setLowPower(int lowPower)
    {
        this.lowPower = lowPower;
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
