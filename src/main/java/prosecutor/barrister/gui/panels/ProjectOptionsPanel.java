package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.components.FFileChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class ProjectOptionsPanel extends JPanel {

    public ProjectOptionsPanel()
    {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints c=new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.gridx=0;
        c.gridy=0;
        //First line
        c.weightx=0.5;
        JLabel label = new JLabel(R().getString("ProjectName"),SwingConstants.LEFT);
        add(label,c);
        c.gridx++;
        c.weightx=1;
        JTextField field = new JTextField();
        add(field,c);

        c.gridx=0;
        c.gridy++;
        //Second line
        c.weightx=0.5;
        label = new JLabel(R().getString("OutputLocation"),SwingConstants.LEFT);
        add(label,c);
        c.gridx++;
        c.weightx=1;
        FFileChooser fileChooser =new FFileChooser();
        add(fileChooser,c);

    }
}
