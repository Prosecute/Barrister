package prosecutor.barrister.gui.components;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by Fry on 15.11.2016.
 */
public class FGroupBox extends JPanel {
    public FGroupBox()
    {
        setBorder(BorderFactory.createTitledBorder(""));
    }
    public FGroupBox(String title)
    {
        setBorder(BorderFactory.createTitledBorder(title));
    }
}
