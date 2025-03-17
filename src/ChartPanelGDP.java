//PART 2

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartPanelGDP extends JPanel
{
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    //Used AI to generate the bottom half of this list of colors
    //to include more options so they dont repeat so often
    private static final Color[] COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN,
            Color.PINK, Color.YELLOW, Color.DARK_GRAY, Color.BLACK, Color.LIGHT_GRAY,

            new Color(139, 69, 19),   // Brown
            new Color(75, 0, 130),    // Indigo
            new Color(255, 140, 0),   // Dark Orange
            new Color(0, 255, 127),   // Spring Green
            new Color(220, 20, 60),   // Crimson
            new Color(0, 191, 255),   // Deep Sky Blue
            new Color(128, 0, 128),   // Purple
            new Color(255, 215, 0),   // Gold
            new Color(34, 139, 34),   // Forest Green

            new Color(70, 130, 180),  // Steel Blue
            new Color(255, 20, 147),  // Deep Pink
            new Color(154, 205, 50),  // Yellow Green
            new Color(255, 99, 71),   // Tomato
            new Color(210, 105, 30),  // Chocolate
            new Color(240, 128, 128), // Light Coral
            new Color(100, 149, 237), // Cornflower Blue
            new Color(186, 85, 211),  // Medium Orchid
            new Color(47, 79, 79),    // Dark Slate Gray
            new Color(173, 216, 230), // Light Blue
            new Color(218, 165, 32),  // Goldenrod
            new Color(255, 69, 0),    // Red-Orange
            new Color(46, 139, 87),   // Sea Green
            new Color(138, 43, 226),  // Blue Violet
            new Color(250, 128, 114), // Salmon
            new Color(64, 224, 208),  // Turquoise
            new Color(245, 222, 179), // Wheat
            new Color(199, 21, 133),  // Medium Violet Red
            new Color(127, 255, 212), // Aquamarine
            new Color(112, 128, 144), // Slate Gray
            new Color(0, 255, 255),   // Aqua
            new Color(255, 228, 196), // Bisque
            new Color(85, 107, 47)    // Dark Olive Green
    };


    public ChartPanelGDP(List<DataItem> dataItems)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("GDP Trends"));

        dataset = new DefaultCategoryDataset();

        chart = ChartFactory.createLineChart(
                "GDP Over Time",
                "Year",
                "GDP (US$)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        updateChart(dataItems);
    }

    public void updateChart(List<DataItem> dataItems)
    {
        dataset.clear();

        if (dataItems.isEmpty())
        {
            return;
        }

        //Add multi colored lines for multiple countries selected
        Map<String, Map<Integer, Double>> countryYearGDPMap = dataItems.stream()
                .collect(Collectors.groupingBy(DataItem::getCountry,
                        Collectors.groupingBy(DataItem::getYear,
                                Collectors.summingDouble(DataItem::getGDP))));

        //Sort countries alphabetically below my chart
        List<String> sortedCountries = new ArrayList<>(countryYearGDPMap.keySet());
        Collections.sort(sortedCountries);

        //Group by country and then the year
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        int colorIndex = 0;
        for (String country : sortedCountries) {
            Map<Integer, Double> yearlyGDP = countryYearGDPMap.get(country);

            for (Map.Entry<Integer, Double> entry : yearlyGDP.entrySet()) {
                dataset.addValue(entry.getValue(), country, String.valueOf(entry.getKey()));
            }

            renderer.setSeriesPaint(colorIndex, COLORS[colorIndex % COLORS.length]);
            colorIndex++;
        }
    }
}

