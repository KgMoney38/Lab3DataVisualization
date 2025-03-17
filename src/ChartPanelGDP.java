//PART 2

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartPanelGDP extends JPanel
{
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    public ChartPanelGDP(List<DataItem> dataItems)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("GDP Trends"));

        dataset = new DefaultCategoryDataset();
        updateChart(dataItems);

        chart = ChartFactory.createLineChart(
                "GDP Over Time",
                "Year",
                "GDP (US$)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void updateChart(List<DataItem> dataItems) {
        dataset.clear();

        //Add multi colored lines for multiple countries selected
        Map<String, Map<Integer, Double>> countryYearGDPMap = dataItems.stream()
                .collect(Collectors.groupingBy(DataItem::getCountry,
                        Collectors.groupingBy(DataItem::getYear,
                                Collectors.summingDouble(DataItem::getGDP))));

        int colorIndex = 0;
        String[] colors = {"#FF0000", "#0000FF", "#008000", "#FFA500", "#800080"};

        for (String country : countryYearGDPMap.keySet()) {
            Map<Integer, Double> yearlyGDP = countryYearGDPMap.get(country);

            for (Map.Entry<Integer, Double> entry : yearlyGDP.entrySet()) {
                dataset.addValue(entry.getValue(), country, String.valueOf(entry.getKey()));
            }

            if (colorIndex < colors.length) {
                chart.getCategoryPlot().getRenderer().setSeriesPaint(colorIndex, Color.decode(colors[colorIndex]));
                colorIndex++;
            }
        }
    }
}

