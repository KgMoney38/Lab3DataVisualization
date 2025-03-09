import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

//Class to display my data in the table
public class TablePanel extends JPanel
{
    //Create my table and model to manage data entries
    private JTable table;
    private DefaultTableModel tableModel;

    //Had to add this because i didnt like the way scientific notation was displayed
    private static final DecimalFormat df = new DecimalFormat("$#,###.00");

    //Initialize data in my table
    public TablePanel (List<DataItem> dataItems)
    {
        setLayout(new BorderLayout());

        //Name for each column
        String[] columnNames = {"Country", "GDP (In USD)", "Year"};

        //Put the headers in my table
        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel);

        //Make table scrollable because of so many entries
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Put data in my table
        updateTable(dataItems);
    }

    //Add new data entries
    public void updateTable(List<DataItem> dataItems)
    {
        tableModel.setRowCount(0);
        dataItems.forEach(item ->
        tableModel.addRow(new Object[] {item.getCountry(), formatGDP(item.getGDP()), item.getYear()
        })
        );
    }

    //Solely to format my gdp in decimal
    private String formatGDP(double gdp)
    {
        return df.format(gdp);
    }
}
