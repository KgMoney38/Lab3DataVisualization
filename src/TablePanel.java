import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class TablePanel extends JPanel
{
    private JTable table;
    private DefaultTableModel tableModel;

    //Had to add this because i didnt like the way scientific notation was displayed
    private static final DecimalFormat df = new DecimalFormat("#,###.00");

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
        tableModel.addRow(new Object[] {item.getCountry(), formatGDP(item.getGDP()), item.getYear()
        })
        );
    }

    //Solely to format my gdp properly
    private String formatGDP(double gdp)
    {
        return df.format(gdp);
    }
}
