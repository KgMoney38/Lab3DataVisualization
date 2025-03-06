public class DataItem
{
    private String country;
    private double gdp;
    private int year;

    public DataItem(String itemName, double itemValue, int year)
    {
        this.country = itemName;
        this.gdp = itemValue;
        this.year = year;
    }

    public String getCountry()
    {
        return country;
    }

    public double getGDP()
    {
        return gdp;
    }

    public int getYear()
    {
        return year;
    }
}
