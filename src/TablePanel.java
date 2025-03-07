import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel
{
    private JTable table;
    private DefaultTableModel tableModel;

    public TablePanel (List<DataItem> dataItems)
    {
        setLayout(new BorderLayout());
        String[] columnNames = {"Country", "GDP", "Year"};

        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        updateTable(dataItems);
    }

    public void updateTable(List<DataItem> dataItems)
    {
        tableModel.setRowCount(0);
        dataItems.forEach(item ->
        tableModel.addRow(new Object[] {item.getCountry(), item.getGDP(), item.getYear()})
        );
    }
}
