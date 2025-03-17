import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
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
    private StatsPanel statsPanel;
    private List<DataItem> originalData;

    //Country Filter
    private JButton countryFilterButton;
    private JPopupMenu countryPopupMenu;
    private Map<String, JCheckBox> countryCheckBoxes = new HashMap<>();
    private JButton uncheckAllButton;

    public SortAndFilter(List<DataItem> dataItems, TablePanel tablePanel, ChartPanelGDP chartPanel, StatsPanel statsPanel, DetailsPanel detailsPanel)
    {
        this.originalData = dataItems;
        this.tablePanel = tablePanel;
        this.chartPanel = chartPanel;
        this.detailsPanel = detailsPanel;
        this.statsPanel = statsPanel;

        setLayout(new GridLayout(1,3));
        setBorder(BorderFactory.createTitledBorder("Sort and Filter"));

        //Sort drop
        String[] sortChoices = {"Default", "Sort by GDP (Highest)", "Sort by GDP (Lowest)",  "Sort by Year", "Sort by Country (A-Z)", "Sort by Country (Z-A)"};
        sortOptions = new JComboBox<>(sortChoices);
        sortOptions.addActionListener(e -> applySorting());

        //Country filters button
        countryFilterButton = new JButton("Select Countries");
        countryPopupMenu = new JPopupMenu();
        populateCountryFilter(dataItems);

        countryFilterButton.addActionListener(e -> countryPopupMenu.show(countryFilterButton, 0, getHeight()));

        //Uncheck button
        uncheckAllButton = new JButton("Uncheck All");
        uncheckAllButton.addActionListener(e -> uncheckAllCountries());
       //Checkbox filter
        showTop10 = new JCheckBox("Top 10 GDP's List");
        showBottom10 = new JCheckBox("Bottom 10 GDP's List");

        //Uncheck one if the other is checked
        showTop10.addItemListener(e -> {
            if(showTop10.isSelected())
            {
                showBottom10.setSelected(false);
            }
            applyFiltering();
        });
        showBottom10.addItemListener(e -> {
            if(showBottom10.isSelected())
            {
                showTop10.setSelected(false);
            }
            applyFiltering();
        });

        add(sortOptions);
        add(showTop10);
        add(countryFilterButton);
        add(uncheckAllButton);
        add(showBottom10);

    }

    //Populate countries with checkboxes
    private void populateCountryFilter(List<DataItem> dataItems)
    {
        List<String> sortedCountries = dataItems.stream()
                .map(DataItem::getCountry)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(new GridLayout(sortedCountries.size(), 1));
        JScrollPane scrollPane = new JScrollPane(countryPanel);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        for (String country : sortedCountries)
        {
            JCheckBox checkBox = new JCheckBox(country);
            countryCheckBoxes.put(country, checkBox);
            checkBox.addItemListener(e -> applyFiltering());
            countryPanel.add(checkBox);
        }

        countryPopupMenu.add(scrollPane);
    }

    //Uncheck all method
    private void uncheckAllCountries()
    {
        for (JCheckBox checkBox : countryCheckBoxes.values())
        {
            checkBox.setSelected(false);
        }
        applyFiltering();
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

            case "Sort by Country (A-Z)" -> originalData.stream()
                    .sorted(Comparator.comparing(DataItem::getCountry))
                    .collect(Collectors.toList());

            case "Sort by Country (Z-A)" -> originalData.stream()
                    .sorted(Comparator.comparing(DataItem::getCountry).reversed())
                    .collect(Collectors.toList());

            default -> originalData;
        };

        tablePanel.updateTable(sortedData);
        chartPanel.updateChart(sortedData);
    }

    private void applyFiltering()
    {
        List<DataItem> filteredData = new ArrayList<>(originalData);

        //Country Filter
        List<String> selectedCountries = countryCheckBoxes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //Filter Condition
        if(!selectedCountries.isEmpty())
        {
            filteredData= filteredData.stream()
                    .filter(item -> selectedCountries.contains(item.getCountry()))
                    .collect(Collectors.toList());
        }
        if (showTop10.isSelected())
        {
            filteredData = filteredData.stream()
                    .sorted((a,b) -> Double.compare(b.getGDP(), a.getGDP()))
                    .limit(10)
                    .collect(Collectors.toList());
        }
        else if (showBottom10.isSelected())
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
        statsPanel.updateStats(filteredData);
    }
}
