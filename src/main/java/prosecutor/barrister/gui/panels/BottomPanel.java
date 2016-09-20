package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButtonStrip;
import prosecutor.barrister.gui.ProjectFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class BottomPanel extends JPanel {
    public BottomPanel()
    {
        JLabel statusLabel = new JLabel("status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(statusLabel);

        JCommandButtonStrip strip =new JCommandButtonStrip();

    }
}
