package prosecutor.barrister.gui.panels.trials.sourcecode;

import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.FGroupBox;
import prosecutor.barrister.gui.components.FLabeledComponent;
import prosecutor.barrister.languages.Language;

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
        c.weighty=1;
        c.gridy=1;
        c.fill=GridBagConstraints.BOTH;
        add(setupLanguageSettings(),c);

    }

    public FGroupBox setupLanguageSettings()
    {
        FGroupBox box=new FGroupBox(ProjectFrame.R().getString("LanguageSettings"));
        box.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.NORTHWEST;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=1;
        c.insets=new Insets(10,0,0,0);
        c.gridx=c.gridy=0;
        box.add(new FLabeledComponent<JComboBox<String>>(ProjectFrame.R().getString("Language"),150,new JComboBox<String>(
                Language.getLanguageCodes()
        )),c);
        c.gridy=1;
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setEnabled(false);
        box.add(new FLabeledComponent<JComboBox>(ProjectFrame.R().getString("LanguageVersion"),150,comboBox),c);
        comboBox = new JComboBox<>(new String[] {ProjectFrame.R().getString("TrialModeTokenCompare"),
                ProjectFrame.R().getString("TrialModeNamingAndCommentsCompare")});
        c.gridy=2;
        box.add(new FLabeledComponent<JComboBox>(ProjectFrame.R().getString("LanguageMode"),150,comboBox),c);
        return box;
    }
}
