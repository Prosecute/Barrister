package prosecutor.barrister.gui.panels.trials.sourcecode;

import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.FGroupBox;
import prosecutor.barrister.gui.components.FLabeledComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fry on 15.11.2016.
 */
public class SourceCodeMainPanel extends JPanel {

    FLabeledComponent<JTextField> trialName;

    public SourceCodeMainPanel()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill=GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.PAGE_START;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.weightx=1.0;
        c.gridx=c.gridy=0;
        trialName=new FLabeledComponent<>(ProjectFrame.R().getString("TrialsName"),new JTextField("Trial"));
        add(trialName,c);
        FGroupBox box=new FGroupBox("Settings");
        c.weighty=1;
        c.gridy=1;
        c.fill=GridBagConstraints.BOTH;
        add(box,c);

    }

    public FGroupBox setupLanguageSettings()
    {
        FGroupBox box=new FGroupBox(ProjectFrame.R().getString("LanguageSettings"));
        box.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.PAGE_START;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.weightx=1;
        c.gridx=c.gridy=0;
        return box;
    }
}
