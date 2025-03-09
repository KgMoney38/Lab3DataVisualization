import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Class to read my csv file and parse the contents into DataItem objects
public class DataLoader {
    public static List<DataItem> loadData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            //Read the first line for the headers
            String[] headers = br.readLine().split(",");

            //Read and process each subsequent line as a data entry
            List<DataItem> dataItems = br.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 2)
                    .flatMap(parts -> {
                        String country = parts[0];

            //Go through remaining columns to get GDP by year
            return IntStream.range(1,parts.length)
                    .mapToObj(index -> {
                        String value = parts[index].trim().replace(",", "");

                        //Had to add as some countries didnt have reported gdps for a couple of the earlier years in my table
                        if(value.equals("..") || value.isEmpty()) return null;

                        try {
                            //Convert GDP to double to get it to display in decimal format
                            double gdp = Double.parseDouble(value);
                            int year = Integer.parseInt(headers[index].trim());
                            return new DataItem(country, gdp, year);
                        } catch (NumberFormatException e) {
                            //Ignore any entry that is improperly formated and notify through the commandline
                            System.err.println("Skipping invalid entry: " + value);
                            return null;
                        }
                    })
                    //Remove any null values
                    .filter(Objects::nonNull);

                    })
                    .collect(Collectors.toList());

            //Fix my sort to go by year not by country
            dataItems.sort(Comparator.comparingInt(DataItem::getYear)
                    .thenComparing(DataItem::getCountry));

            return dataItems;

        } catch (IOException e) {
            //Return an empty list if reading my file fails
            System.err.println("Error reading file: " + fileName);
            return new ArrayList<>();
        }
    }

    //Test method for my data loading process
    public static void main(String[] args)
    {
        List<DataItem> data = loadData("src/PopularIndicators.csv");

        //Label like header to explain what each item is
        System.out.println("             Country    , GDP in US$    , Year");

        //Print my first entry
        data.stream().findFirst().ifPresent(item ->
                System.out.println("First Entry: " + format(item))
        );

        //Print my tenth entry
        data.stream().skip(9).findFirst().ifPresent(item ->
                System.out.println("Tenth Entry: " + format(item))
        );

        //Not for why formatting for gdp is different in my csv than my table
        //I just prefer the way decimal notation looks
        System.out.println("NOTE: AMOUNT IS IN SCIENTIFIC NOTATION ABOVE (Converted to Decimal in My Table)");

        //List total size of my data set
        System.out.println("Total Data Entries: " + data.size());

    }

    //Format my DataItems objects into readable strings
    private static String format(DataItem dataItem)
        {
            return dataItem.getCountry() + ", " + dataItem.getGDP() + ", " + dataItem.getYear();
        }
}
