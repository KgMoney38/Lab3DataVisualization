public class DataItem
{
    private String itemName;
    private double itemValue;
    private int year;

    public DataItem(String itemName, double itemValue, int year)
    {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.year = year;
    }

    public String getItemName()
    {
        return itemName;
    }

    public double getItemValue()
    {
        return itemValue;
    }

    public int getYear()
    {
        return year;
    }
}
