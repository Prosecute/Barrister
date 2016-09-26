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
    int Return;
    public static int RESULT_OK=1;
    public static int RESULT_CANCEL=0;

    public TrialConfiguration getConfiguration()
    {
        return configuration;
    }

    public SourceCodeTrialDialog(TrialConfiguration trialConfiguration, Window owner)
    {
        super(owner,trialConfiguration==null?R().getString("AddTrial"):R().getString("ChangeTrial"),ModalityType.APPLICATION_MODAL);
        this.configuration=trialConfiguration;
        if(trialConfiguration==null) {
            configuration = new TrialConfiguration();
            configuration.setTrialType(new TrialConfiguration.TrialType());
        }
        prepareUI();
    }
    public int showDialog()
    {
        setVisible(true);
        return Return;
    }

    public void prepareUI()
    {
        setLayout(new BorderLayout());
        Dimension dimension=new Dimension(620,480);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setSize(dimension);
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
