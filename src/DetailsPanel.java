//PART 2

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel
{
    private JLabel countryLabel, gdpLabel, yearLabel;

    public DetailsPanel()
    {
        setLayout(new GridLayout(3,1));
        setBorder(BorderFactory.createTitledBorder("Details"));

        countryLabel = new JLabel("Country");
        gdpLabel = new JLabel("GDP");
        yearLabel = new JLabel("Year");

        add(countryLabel);
        add(gdpLabel);
        add(yearLabel);
    }

    public void updateDetails(DataItem item)
    {
        countryLabel.setText("Country: " + item.getCountry());
        gdpLabel.setText("GDP: " + item.getGDP());
        yearLabel.setText("Year: " + item.getYear());
    }
}
