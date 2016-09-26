package prosecutor.barrister.gui.forms;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.TrialConfiguration;

import javax.swing.*;
import java.awt.*;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class SourceCodeTrialDialog extends JDialog {

    TrialConfiguration configuration;

    public TrialConfiguration getConfiguration()
    {
        return configuration;
    }

    public SourceCodeTrialDialog()
    {
        configuration=new TrialConfiguration();
        configuration.setTrialType(new TrialConfiguration.TrialType());
        configuration.setExtensions(new TrialConfiguration.Extensions());
    }

    public SourceCodeTrialDialog(TrialConfiguration configuration)
    {
        this.configuration=configuration;
    }

    public void prepareUI()
    {
        setLayout(new BorderLayout());
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonOK=new JButton(R().getString("OK"));
        JButton buttonCancel=new JButton(R().getString("Cancel"));
        bottom.add(buttonOK);
        bottom.add(buttonCancel);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelTrial = new JPanel();

        add(tabbedPane, BorderLayout.CENTER);
        add(bottom,BorderLayout.AFTER_LAST_LINE);
    }
}
