//PART 2

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static java.awt.AWTEventMulticaster.add;

public class StatsPanel extends JPanel
{
private JLabel avgGDPLabel, maxGDPLabel, minGDPLabel;
private static final DecimalFormat df = new DecimalFormat("$#,###.00");

public StatsPanel(List<DataItem> dataItems)
{
    setLayout(new GridLayout(3, 1));
    setBorder(BorderFactory.createTitledBorder("GDP Statistics"));

    avgGDPLabel = new JLabel("Avg GDP: Calculating...");
    maxGDPLabel = new JLabel("Max GDP: Calculating...");
    minGDPLabel = new JLabel("Min GDP: Calculating...");

    add(avgGDPLabel);
    add(maxGDPLabel);
    add(minGDPLabel);

    updateStats(dataItems);
}

    public void updateStats(List<DataItem> dataItems)
    {
        OptionalDouble avgGDP = dataItems.stream().mapToDouble(DataItem::getGDP).average();
        OptionalDouble maxGDP = dataItems.stream().mapToDouble(DataItem::getGDP).max();
        OptionalDouble minGDP = dataItems.stream().mapToDouble(DataItem::getGDP).min();

        avgGDPLabel.setText("Average GDP: " + (avgGDP.isPresent() ? df.format(avgGDP.getAsDouble()) : "N/A"));
        maxGDPLabel.setText("Highest GDP: " + (maxGDP.isPresent() ? df.format(maxGDP.getAsDouble()) : "N/A"));
        minGDPLabel.setText("Lowest GDP: " + (minGDP.isPresent() ? df.format(minGDP.getAsDouble()) : "N/A"));
    }
}
