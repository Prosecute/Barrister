package prosecutor.barrister.gui.components.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.Align;
import prosecutor.barrister.gui.components.FComponent;
import prosecutor.barrister.gui.components.FLabel;
import prosecutor.barrister.gui.listener.MouseClickListener;

import javax.swing.*;
import java.awt.*;

public class FSidePanel extends JSplitPane {

    FSideMainPanel main;
    FSideMinimalizedPanel minimalizedPanel;
    protected  final StringBuffer Title, MinimalizedTitl;

    public FSidePanel(String title)
    {
        this(title,"");
    }
    public FSidePanel(String title, String minimalizedTitle)
    {
        Title=new StringBuffer(title);
        MinimalizedTitl=new StringBuffer(minimalizedTitle);
    }

    public void minimalize()
    {
        if(getLeftComponent()==main)
            return;
        main.setVisible(false);
        setLeftComponent(minimalizedPanel);
        minimalizedPanel.setVisible(true);
    }
    public void maximalize()
    {
        if(getLeftComponent()==minimalizedPanel)
            return;
        minimalizedPanel.setVisible(false);
        setLeftComponent(main);
        main.setVisible(true);
    }

    public FSidePanel()
    {
        this("","");
    }
    private class FSideMainPanel extends FPanel
    {
        public FSideMainPanel()
        {
            addMouseClickListener(e -> {minimalize();});
        }

        private class FsideHeader extends FComponent
        {
            public FsideHeader()
            {
                this.add(new FLabel(Title).setAlign(Align.LEFT));
                this.add(new FLabel(">>").setAlign(Align.RIGHT).addMouseClickListener(e -> {minimalize();}));
            }
        }
    }
    private class FSideMinimalizedPanel extends FPanel
    {

        public FSideMinimalizedPanel()
        {
            addMouseClickListener(e -> {maximalize();});
        }

        @Override
        public void paint(Graphics g) {

        }
    }
}
