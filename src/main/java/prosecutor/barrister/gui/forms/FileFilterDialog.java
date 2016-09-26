package prosecutor.barrister.gui.forms;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import prosecutor.barrister.jaxb.FileFilter;
import prosecutor.barrister.jaxb.FilteredParameter;

import javax.swing.*;
import java.awt.*;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class FileFilterDialog extends JDialog {


    public FileFilter FileFilter;
    public int Result;
    public static int RESULT_OK=1;
    public static int RESULT_CANCEL=0;

    public int showDialog()
    {
        setVisible(true);
        return Result;
    }

    public FileFilterDialog(FileFilter fileFilter,Window parent)
    {
        super(parent, R().getString(fileFilter == null ? "AddFileFilter" : "ChangeFileFilter"), ModalityType.APPLICATION_MODAL);
        if(fileFilter==null)
            fileFilter=new FileFilter();
        this.FileFilter=fileFilter;
        Dimension dimension=new Dimension(620,480);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setSize(dimension);
        prepareUI();
    }

    JTextField Name,Regex;
    JComboBox Mode;
    JCheckBox c0,c1,c2,c3,c4,c5;

    private void prepareUI()
    {
        String[] op = new String[]{"<",">","<=",">=","!=","=="};
        String[] op2 = new String[]{"startWith","endWith","contains","notContains"};

        setLayout(new BorderLayout());
        JPanel bottomPanel=new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonOK = new JButton(R().getString("OK"));
        JButton buttonCancel = new JButton(R().getString("Cancel"));
        bottomPanel.add(buttonOK);
        bottomPanel.add(buttonCancel);

        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.weightx=c.weighty=1.0;
        c.gridheight=c.gridwidth=1;
        c.gridx=c.gridy=0;
        add(new JLabel(R().getString("Name")),c);
        c.gridwidth=2;
        Name = new JTextField(FileFilter.getName());
        c.gridx=1;
        add(Name,c);

        c.gridx=0;c.gridy=1;
        c.gridwidth=1;
        add(new JLabel(R().getString("Mode")));
        c.gridx=1;
        Mode=new JComboBox(new String[]{R().getString("ModeExclude"),R().getString("ModeInclude")});
        c.gridwidth=2;
        Mode.setSelectedIndex(0);
        add(Mode, c);

        c.gridx=0;c.gridy=2;
        c.gridwidth=1;
        c0=new JCheckBox();
        add(c0,c);
        c.gridx=1;
        add(new JLabel(R().getString("RegexFilter")));
        c.gridx=2;
        c.gridwidth=2;
        Regex=new JTextField(FileFilter.getRegex());
        add(Regex,c);

        c.gridx=0;
        c.gridwidth=1;
        c.gridy=3;
        c1=new JCheckBox();
        add(c1,c);
        c.gridx=1;
        add(new JLabel(R().getString("FileModifiedDate")));

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.AFTER_LAST_LINE);
    }


}
