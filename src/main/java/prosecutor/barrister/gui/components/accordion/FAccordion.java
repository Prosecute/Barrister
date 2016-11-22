package prosecutor.barrister.gui.components.accordion;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.ConstructorProperties;

public class FAccordion extends JComponent {
    private JPanel panel=new JPanel();
    private FAccordionHeader header;

    public FAccordion(String title)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.weightx=1;c.weighty=0;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=c.gridy=0;
        header=new FAccordionHeader(title);
        add(header,c);
        c.gridy=1;
        add(panel,c);
        panel.setLayout(new GridBagLayout());
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Switch();
            }
        });
    }
    public void Switch()
    {
        panel.setVisible(!panel.isVisible());
        int ik=0;
    }

    public void addButton(FAccordionButton button)
    {
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.weightx=c.weighty=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=c.gridy=0;
        c.gridy=panel.getComponentCount();
        panel.add(button,c);
    }

    private class FAccordionHeader extends JButton
    {

        @ConstructorProperties({"text"})
        public FAccordionHeader(String text) {
            super(text);
        }
    }
}
