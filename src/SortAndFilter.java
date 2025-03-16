import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortAndFilter extends JPanel
{
    private JComboBox<String> sortOptions;
    private JCheckBox showTop10;
    private JCheckBox showBottom10;
    private TablePanel tablePanel;
    private ChartPanelGDP chartPanel;
    private DetailsPanel detailsPanel;
    private List<DataItem> originalData;

    public SortAndFilter(List<DataItem> dataItems, TablePanel tablePanel, ChartPanelGDP chartPanel, DetailsPanel detailsPanel)
    {
        this.originalData = dataItems;
        this.tablePanel = tablePanel;
        this.chartPanel = chartPanel;
        this.detailsPanel = detailsPanel;

        setLayout(new GridLayout(1,3));
        setBorder(BorderFactory.createTitledBorder("Sort and Filter"));

        //Sort drop
        String[] sortChoices = {"Default", "Sort by GDP (Highest)", "Sort by GDP (Lowest)",  "Sort by Year"};
        sortOptions = new JComboBox<>(sortChoices);
        sortOptions.addActionListener(e -> applySorting());

       //Checkbox filter
        showTop10 = new JCheckBox("Top 10 GDP");
        showBottom10 = new JCheckBox("Bottom 10 GDP");

        showTop10.addItemListener(e -> applyFiltering());
        showBottom10.addItemListener(e -> applyFiltering());

        add(sortOptions);
        add(showTop10);
        add(showBottom10);

    }

    private void applySorting()
    {
        List<DataItem> sortedData = switch (sortOptions.getSelectedItem().toString())
        {
            case "Sort by GDP (Highest)" -> originalData.stream()
                    .sorted((a, b) -> Double.compare(b.getGDP(), a.getGDP()))
                    .collect(Collectors.toList());

            case "Sort by GDP (Lowest)" -> originalData.stream()
                    .sorted((a, b) -> Double.compare(a.getGDP(), b.getGDP()))
                    .collect(Collectors.toList());

            case "Sort by Year" -> originalData.stream()
                    .sorted((a, b) -> Integer.compare(a.getYear(), b.getYear()))
                    .collect(Collectors.toList());

            default -> originalData;
        };

        tablePanel.updateTable(sortedData);
        chartPanel.updateChart(sortedData);
    }

    private void applyFiltering()
    {
        List<DataItem> filteredData = originalData;

        //Filter Condition
        if (showTop10.isSelected())
        {
            filteredData = filteredData.stream()
                    .sorted((a,b) -> Double.compare(b.getGDP(), a.getGDP()))
                    .limit(10)
                    .collect(Collectors.toList());
        }
        if (showBottom10.isSelected())
        {
            filteredData = filteredData.stream()
                    .sorted((a,b) -> Double.compare(a.getGDP(), b.getGDP()))
                    .limit(10)
                    .collect(Collectors.toList());
        }

        //Sorting Conditions
        String selectedsort = (String) sortOptions.getSelectedItem();

        if("Sort by Country".equals(selectedsort))
        {
            filteredData.sort(Comparator.comparing(DataItem::getCountry));
        }
        else if("Sort by GDP".equals(selectedsort))
        {
            filteredData.sort(Comparator.comparing(DataItem::getGDP).reversed());
        }
        else if("Sort by Year".equals(selectedsort))
        {
            filteredData.sort(Comparator.comparing(DataItem::getYear));
        }

        //Update Table
        tablePanel.updateTable(filteredData);
        chartPanel.updateChart(filteredData);
    }
}
