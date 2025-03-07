public class DataItem
{
    private String country;
    private double gdp;
    private int year;

    public DataItem(String country, double gdp, int year)
    {
        this.country = country;
        this.gdp = gdp;
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
