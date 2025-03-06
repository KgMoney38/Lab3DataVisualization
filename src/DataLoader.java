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

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] tokens = line.split(",");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String country = parts[0];

                for (int i = 1; i < parts.length; i++) {
                    String value = parts[i].trim().replace(",", "");

                    if (!value.equals("..") && !value.isEmpty()) {
                        try {
                            double gdp = Double.parseDouble(value);
                            int year = Integer.parseInt(tokens[i].trim());
                            data.add(new DataItem(country, gdp, year));
                        } catch (NumberFormatException e) {
                            System.out.println("Error in parsing data");
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

    public static void min(String[] args)
    {
        List<DataItem> dataItems = loadData("data.csv");

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
