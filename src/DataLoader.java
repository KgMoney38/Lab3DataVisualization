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
            String line = br.readLine();
            if (line == null)
            {
                System.out.println("File empty");
                return data;
            }

            String[] tokens = line.split(",");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length < 2)
                {
                    continue;
                }

                String country = parts[0];

                for (int i = 1; i < Math.min(parts.length, tokens.length); i++) {
                    String value = parts[i].trim().replace(",", "");

                    if (!value.equals("..") && !value.isEmpty()) {
                        try {
                            double gdp = Double.parseDouble(value);
                            int year = Integer.parseInt(parts[i].trim());
                            data.add(new DataItem(country, gdp, year));
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing data");
                        }
                    }
                }
            }
        }catch (IOException e)
        {
            System.out.println("Error reading file");
        }
        return data;
    }

    public static void main(String[] args)
    {
        List<DataItem> dataItems = DataLoader.loadData("myData.csv");

        System.out.println("Total Data Entries: " + dataItems.size());

        if(!dataItems.isEmpty())
        {
            System.out.println("First Entry: " + format(dataItems.get(0)));
        }

        if (dataItems.size() >= 10)
        {
            System.out.println("Tenth Entry: " + format(dataItems.get(9)));
        }
    }

    private static String format(DataItem dataItem)
    {
        return dataItem.getCountry() + ", " + dataItem.getGDP() + ", " + dataItem.getYear();
    }
}
