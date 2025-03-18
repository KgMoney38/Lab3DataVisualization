//PART 2

import javax.swing.*;
import java.awt.*;
import java.util.List;

//Basic class to display details of individual selected rows
public class DetailsPanel extends JPanel
{
    private JLabel countryLabel;
    private JLabel gdpLabel;
    private JLabel yearLabel;

    public DetailsPanel(List<DataItem> dataItems)
    {
        setLayout(new GridLayout(3,1));
        setBorder(BorderFactory.createTitledBorder("Selected Country GDP Details"));

        countryLabel = new JLabel("Country: ");
        gdpLabel = new JLabel("GDP: ");
        yearLabel = new JLabel("Year: ");

        add(countryLabel);
        add(gdpLabel);
        add(yearLabel);
    }

    public void updateDetails(String country, String gdp, String year)
    {
        countryLabel.setText("Country: " + country);
        gdpLabel.setText("GDP: " + gdp);
        yearLabel.setText("Year: " + year);
    }
}
