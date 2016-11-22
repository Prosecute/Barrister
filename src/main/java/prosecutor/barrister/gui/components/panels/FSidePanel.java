package prosecutor.barrister.gui.components.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.swing.*;
import java.awt.*;

public class FSidePanel extends JPanel {

    private JPanel mainContent,minimalizedContent;

    public FSidePanel(String title)
    {
        this(title,"");
    }
    public FSidePanel(String title, String minimalizedTitle)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.PAGE_START;
        c.gridy=c.gridx=0;
        c.weightx=c.weighty=0;
        c.fill=GridBagConstraints.HORIZONTAL;
    }
    public FSidePanel()
    {
        this("","");
    }
    private class FSideMainPanel extends JPanel
    {

    }
    private class FSideMinimalizedPanel extends JPanel
    {

    }
}
