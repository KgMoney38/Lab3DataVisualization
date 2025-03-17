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

    avgGDPLabel = new JLabel("Avg GDP: ");
    maxGDPLabel = new JLabel("Max GDP: ");
    minGDPLabel = new JLabel("Min GDP: ");

    add(avgGDPLabel);
    add(maxGDPLabel);
    add(minGDPLabel);

    updateStats(dataItems);
}

    public void updateStats(List<DataItem> dataItems)
    {

        if (dataItems.isEmpty()) return;

        double avg = dataItems.stream().mapToDouble(DataItem::getGDP).average().orElse(0);
        double max = dataItems.stream().mapToDouble(DataItem::getGDP).max().orElse(0);
        double min = dataItems.stream().mapToDouble(DataItem::getGDP).min().orElse(0);

        avgGDPLabel.setText("Average GDP: " + df.format(avg));
        maxGDPLabel.setText("Highest GDP: " + df.format(max));
        minGDPLabel.setText("Lowest GDP: " + df.format(min));


    }
}
