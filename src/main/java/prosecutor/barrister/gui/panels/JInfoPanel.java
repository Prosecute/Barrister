package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import sun.font.FontFamily;

import javax.swing.*;
import javax.swing.text.StringContent;
import java.awt.*;

public class JInfoPanel extends JPanel {

    public JInfoPanel(String content)
    {
        setLayout(new GridBagLayout());
        JLabel label=new JLabel(content);
        label.setFont(new Font(Font.SERIF,Font.PLAIN,32));
        add(label, new GridBagConstraints());
    }
}
