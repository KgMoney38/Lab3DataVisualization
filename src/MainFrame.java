import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Main GUI window for my table
public class MainFrame extends JFrame
{
    private TablePanel tablePanel;

    public MainFrame(List<DataItem> dataItems)
    {
        //Set my window properties
        setTitle("Country GDP's for the Years 2000-2015");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tablePanel = new TablePanel(dataItems);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    //Launch the GUI
    public static void main(String[] args)
    {
        List<DataItem> dataItems = DataLoader.loadData("src/CountryGDPs.csv");
        SwingUtilities.invokeLater(() -> new MainFrame(dataItems));
    }
}
