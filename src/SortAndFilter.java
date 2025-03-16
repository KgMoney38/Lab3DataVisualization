import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortAndFilter extends JPanel
{
    private JComboBox<String> sortDropdown;
    private JCheckBox highGDPCheckBox, year2000sCheckBox;
    private JButton applyFilterButton;

    private List<DataItem> originalData;
    private TablePanel tablePanel;

    public void FilterSortPanel(List<DataItem> dataItems, TablePanel tablePanel) {
        this.originalData = dataItems;
        this.tablePanel = tablePanel;

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Sort and Filter"));

        //Sort drop
        String[] sortOptions = {"Sort by Country", "Sort by GDP", "Sort by Year"};
        sortDropdown = new JComboBox<>(sortOptions);
        add(sortDropdown);

        //Filter check
        highGDPCheckBox = new JCheckBox("GDP >1 Trillion");
        year2000sCheckBox = new JCheckBox("Year 2000-2009");
        add(highGDPCheckBox);
        add(year2000sCheckBox);

        //Apply Button
        applyFilterButton = new JButton("Apply Filter(s)");
        applyFilterButton.addActionListener(e -> applyFilters());
        add(applyFilterButton);
    }
    private void applyFilters()
    {
        List<DataItem> filteredDataItems = originalData;

        //Filter Condition
        if (highGDPCheckBox.isSelected())
        {
            filteredDataItems = filteredDataItems.stream().filter(d -> d.getGDP() > 1_000_000_000_000L).collect(Collectors.toList());
        }
        if (year2000sCheckBox.isSelected())
        {
            filteredDataItems= filteredDataItems.stream().filter(d -> d.getYear() >= 2000 && d.getYear() <= 2009).collect(Collectors.toList());
        }

        //Sorting Conditions
        String selectedsort = (String) sortDropdown.getSelectedItem();

        if("Sort by Country".equals(selectedsort))
        {
            filteredDataItems.sort(Comparator.comparing(DataItem::getCountry));
        }
        else if("Sort by GDP".equals(selectedsort))
        {
            filteredDataItems.sort(Comparator.comparing(DataItem::getGDP).reversed());
        }
        else if("Sort by Year".equals(selectedsort))
        {
            filteredDataItems.sort(Comparator.comparing(DataItem::getYear));
        }

        //Update Table
        tablePanel.updateTable(filteredDataItems);
    }
}
