import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame
{
    private TablePanel tablePanel;

    public MainFrame(List<DataItem> dataItems)
    {
        setTitle("Data Visualization: Country GDP's for the Years 2000-2015");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tablePanel = new TablePanel(dataItems);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args)
    {
        List<DataItem> dataItems = DataLoader.loadData("src/PopularIndicators.csv");
        SwingUtilities.invokeLater(() -> new MainFrame(dataItems));
    }
}
