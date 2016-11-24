package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.accordion.FAccordion;
import prosecutor.barrister.gui.components.accordion.FAccordionButton;
import prosecutor.barrister.gui.components.buttons.FButton;
import prosecutor.barrister.gui.components.panels.FPanel;

import javax.swing.*;
import java.awt.*;

public class FAccordionTest {

    public static void main(String... args)
    {
        Form frame = new Form();
        FPanel pane=new FPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=1;c.weighty=0;
        c.gridy=c.gridx=0;

        FAccordion accordion=new FAccordion("Title");
        accordion.addButton(new FButton("sdad",ProjectFrame.getIconIO("oxygen/16x16/documentation.png")){{setStyle("green");}});
        accordion.addButton(new FButton("Data test",ProjectFrame.getIconIO("oxygen/16x16/documentation.png")){{setStyle("green");}});
        accordion.addButton(new FButton("TReas test",ProjectFrame.getIconIO("oxygen/16x16/documentation.png")){{setStyle("green");}});
        pane.add(accordion,c);
        accordion=new FAccordion("Title2");
        accordion.addButton(new FButton("Test2"));
        c.gridy=1;
        pane.add(accordion,c);
        accordion=new FAccordion("Title3");
        accordion.addButton(new FButton("Test2"));
        c.gridy=2;
        pane.add(accordion,c);
        c.weighty=1;
        pane.add(new FPanel(),c);
        JScrollPane scroll=new JScrollPane(pane);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(new JPanel(){{setBackground(Color.blue);}},BorderLayout.CENTER);
        frame.setSize(new Dimension(500,500));
        frame.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-250,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-250);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
