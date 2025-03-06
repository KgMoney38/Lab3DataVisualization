import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader
{
    public static List<DataItem> loadData(String fileName)
    {
        List<DataItem> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            br.lines().forEach(line -> {
                String [] parts = line.split(",");
                if (parts.length == 3)
                {
                    data.add(new DataItem(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2])));
                }
            });
        } catch (IOException e)
        {
            System.out.println("File not found");
        }
            return data;
    }
    public static void min(String[] args)
    {
        List<DataItem> dataItems = DataLoader.loadData(args[0]);

        System.out.println("Total Data Entries: " + dataItems.size());

        if(!dataItems.isEmpty())
        {
            System.out.println("First Item: " + format(dataItems.get(0)));
        }

        if (dataItems.size() >= 10)
        {
            System.out.println("Tenth Item: " + format(dataItems.get(9)));
        }
    }

    private static String format(DataItem dataItem)
    {
        return dataItem.getItemName() + ", " + dataItem.getItemValue() + ", " + dataItem.getYear();
    }
}
