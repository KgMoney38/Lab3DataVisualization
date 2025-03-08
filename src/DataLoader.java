import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataLoader {
    public static List<DataItem> loadData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String[] headers = br.readLine().split(",");

            List<DataItem> dataItems = br.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 2)
                    .flatMap(parts -> {
                        String country = parts[0];

            return IntStream.range(1,parts.length)
                    .mapToObj(index -> {
                        String value = parts[index].trim().replace(",", "");

                        if(value.equals("..") || value.isEmpty()) return null;

                        try {
                            double gdp = Double.parseDouble(value);
                            int year = Integer.parseInt(headers[index].trim());
                            return new DataItem(country, gdp, year);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid entry: " + value);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);

                    })
                    .collect(Collectors.toList());

            //Fix my sort to go by year not by country
            dataItems.sort(Comparator.comparingInt(DataItem::getYear)
                    .thenComparing(DataItem::getCountry));

            return dataItems;

        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            return new ArrayList<>();
        }
    }

    public static void main(String[] args)
    {
        List<DataItem> data = loadData("src/PopularIndicators.csv");
        System.out.println("Total Data Entries: " + data.size());

        data.stream().findFirst().ifPresent(item ->
                System.out.println("First Entry: " + format(item))
        );

        data.stream().skip(9).findFirst().ifPresent(item ->
                System.out.println("Tenth Entry: " + format(item))
        );
    }

    private static String format(DataItem dataItem)
        {
            return dataItem.getCountry() + ", " + dataItem.getGDP() + ", " + dataItem.getYear();
        }
}
