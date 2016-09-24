package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////



import org.pushingpixels.substance.api.SubstanceLookAndFeel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TitledPanel extends JPanel {

    public JPanel TitlePanel,SubTitlePanel;
    private JComponent MainComponent;

    public void changeMainComponent(JComponent newMain)
    {
        if(MainComponent!=null) {
            remove(MainComponent);
            MainComponent=null;
        }
        if(newMain!=null)
        {
            MainComponent=newMain;
            add(MainComponent);
        }

    }

    public TitledPanel(String title, boolean bool,JComponent main,JComponent... subtitle)
    {
        Color color=new Color(149, 179, 215);
        TitlePanel =new JPanel();
        TitlePanel.setSize(TitlePanel.getWidth(),48);
        TitlePanel.setMinimumSize(new Dimension(TitlePanel.getMinimumSize().width,48));
        TitlePanel.setMaximumSize(new Dimension(TitlePanel.getMaximumSize().width, 48));
        TitlePanel.setBackground(color);
        TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(color,1,true)));

        JLabel label =new JLabel(title);
        label.putClientProperty(SubstanceLookAndFeel.COLORIZATION_FACTOR, 1.0);
        label.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBorder(new EmptyBorder(0,20,0,0));
        TitlePanel.add(label);

        add(TitlePanel);
        if(subtitle.length>0)
        {
            if(bool) {
                SubTitlePanel =new JPanel();
                SubTitlePanel.setSize(SubTitlePanel.getWidth(), 32);
                SubTitlePanel.setMinimumSize(new Dimension(SubTitlePanel.getMinimumSize().width, 32));
                SubTitlePanel.setMaximumSize(new Dimension(SubTitlePanel.getMaximumSize().width, 32));
                SubTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                SubTitlePanel.setBackground(new Color(179, 209, 245));
            for(JComponent comp:subtitle){
                SubTitlePanel.add(comp);
                comp.setBackground(new Color(179, 209, 245));
            }
            add(SubTitlePanel);
            }
            else
                for(JComponent comp:subtitle)
                    add(comp);
        }
        MainComponent=main;
        if(main!=null)
            add(main);
    }
    public TitledPanel(String title, JComponent main,JComponent... subtitle)
    {
        this(title,true,main,subtitle);
    }
}
