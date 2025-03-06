import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel
{
    private JTable table;
    private DefaultTableModel model;

    public TablePanel (List<DataItem> dataItems)
    {
        setLayout(new BorderLayout());
        String[] columnNames = {"Name", "Value", "Year"};
        model = new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        updateTable(dataItems);
    }
public void updateTable(List<DataItem> dataItems)
{
    model.setRowCount(0);
    for (DataItem dataItem : dataItems)
    {
        model.addRow(new Object[] {dataItem.getCountry(), dataItem.getGDP(), dataItem.getYear()});
    }
}
}
