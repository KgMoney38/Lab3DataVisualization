//PART 2

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

    public void updateChart(List<DataItem> dataItems)
    {
     dataset.clear();
     dataItems.stream()
             .collect(Collectors.groupingBy(DataItem::getYear))
             .forEach((year,list) -> {
                 double totalGDP = list.stream().mapToDouble(DataItem::getGDP).sum();
                 dataset.addValue(totalGDP, "Total GDP", String.valueOf(year));
             });
    }
}

