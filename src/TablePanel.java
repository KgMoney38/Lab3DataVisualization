import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    public TablePanel (List<DataItem> dataItems) {
        setLayout(new BorderLayout());

        //Name for each column
        String[] columnNames = {"Country", "GDP (In US$)", "Year"};

        //Put the headers in my table
        tableModel = new DefaultTableModel(columnNames, 0) {
            //Make table non-editable as this is a dataset
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
       for (DataItem item : dataItems) {
           tableModel.addRow(new Object[]{
                   item.getCountry(),
                   formatGDP(item.getGDP()),
                   item.getYear()
           });
       }
    }

    //To allow my mainFrame access
    public JTable getTable()
    {
        return table;
    }

    //Solely to format my gdp in decimal
    private String formatGDP(double gdp)
    {
        return df.format(gdp);
    }

    //Method to get the data that is currently displayed in my table
    public List<DataItem> getCurrentData() {
        List<DataItem> displayData = new ArrayList<>();
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) 
        {
            String country = (String) tableModel.getValueAt(i, 0);
            Object gdpObject = tableModel.getValueAt(i, 1);
            double gdp;

            if(gdpObject instanceof String)
            {
                gdp = Double.parseDouble(((String) gdpObject).replace("$","").replace(",",""));
            }else
            {
                gdp = (Double) gdpObject;
            }
            
            Object yearObject = tableModel.getValueAt(i, 2);
            int year;
            if(yearObject instanceof String)
            {
                year=Integer.parseInt((String) yearObject);
            }else {
                year = (int)yearObject;
            }
            
            displayData.add(new DataItem(country, gdp, year));
        }

        return displayData;
    }
}
