import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Main GUI window for my table
public class MainFrame extends JFrame
{
    private TablePanel tablePanel;
    private StatsPanel statsPanel;
    private ChartPanelGDP chartPanel;
    private DetailsPanel detailsPanel;
    private SortAndFilter sortAndFilter;


    public MainFrame(List<DataItem> dataItems)
    {
        //Set my window properties
        setTitle("Country GDP's for the Years 2000-2015");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Table Panel
        tablePanel = new TablePanel(dataItems);

        //Stats Panel
        statsPanel = new StatsPanel(dataItems);

        //Chart Panel
        chartPanel = new ChartPanelGDP(dataItems);

        //Details Panel
        detailsPanel = new DetailsPanel(dataItems);

        //Sorting and filtering
        sortAndFilter = new SortAndFilter(dataItems, tablePanel, chartPanel, detailsPanel);

        //Top panel
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(statsPanel);
        topPanel.add(sortAndFilter);

        //Right panel chart and details
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        rightPanel.add(detailsPanel, BorderLayout.SOUTH);

        //Add my panels
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    //Launch the GUI
    public static void main(String[] args)
    {
        List<DataItem> dataItems = DataLoader.loadData("src/CountryGDPs.csv");
        SwingUtilities.invokeLater(() -> new MainFrame(dataItems));
    }
}
