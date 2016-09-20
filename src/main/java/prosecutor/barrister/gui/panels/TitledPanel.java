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
import javax.swing.border.LineBorder;
import java.awt.*;

public class TitledPanel extends JPanel {

    public JPanel TitlePanel,SubTitlePanel;

    public TitledPanel(String title, Color color, JComponent main,JComponent... subtitle)
    {
        TitlePanel =new JPanel();
        TitlePanel.setBackground(color);
        TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(color)));

        JLabel label =new JLabel(title);
        label.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBorder(new EmptyBorder(0,20,0,0));
        TitlePanel.add(label);

        add(TitlePanel,BorderLayout.PAGE_START);
        if(subtitle.length>0)
        {
            SubTitlePanel =new JPanel();
            SubTitlePanel.setLayout(new FlowLayout());
            for(JComponent comp:subtitle)
                SubTitlePanel.add(comp);
            add(SubTitlePanel,BorderLayout.PAGE_START);
        }
        if(main!=null)
        add(main,BorderLayout.CENTER);
    }
}
