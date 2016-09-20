package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class ProjectOverviewPanel extends JPanel {

    public ProjectOverviewPanel()
    {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;

        JLabel label=new JLabel(R().getString("Project")+"  "+"TODO remove");/*TODO Configuration.projectName*/
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        c.gridx=0;
        c.weightx=1;
        c.weighty=0.2;
        c.gridheight=1;
        c.anchor=GridBagConstraints.NORTHWEST;
        c.gridy=0;
        add(label,c);

        c.weighty=1;
        TitledPanel description=new TitledPanel(R().getString("ProjectDescription"),new Color(54,221,54),null);
        c.gridx=0;
        c.gridy=1;
        add(description,c);



        TitledPanel empty=new TitledPanel(R().getString("ProjectDescription"),new Color(221,54,54),null);
        c.gridx=0;
        c.gridy=2;
        add(empty,c);

        TitledPanel metrics=new TitledPanel(R().getString("ProjectOptions"),new Color(54,54,221),null);
        c.gridx=1;
        c.gridy=1;
        c.gridheight=2;
        add(metrics,c);
    }
}
