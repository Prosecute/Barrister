package prosecutor.barrister.gui.components.accordion;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.components.FComponent;
import prosecutor.barrister.gui.managers.UIManager;
import prosecutor.barrister.gui.components.buttons.FButton;
import prosecutor.barrister.gui.components.panels.FPanel;
import prosecutor.barrister.gui.managers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.ConstructorProperties;

public class FAccordion extends FComponent {
    private FPanel panel=new FPanel();
    private FAccordionHeader header;


    public void filter(String filter)
    {
        Component[] components=panel.getComponents();
        boolean visible=false;
        for(Component comp:components)
        {
            FButton button=((FButton)comp);
            button.setVisible(filter==null||filter.equals("") || button.getText().contains(filter));
            if(!visible && button.isVisible())
                visible=true;
        }
        setVisible(visible);
    }

    public FAccordion(String title)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.weightx=1;c.weighty=0;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=c.gridy=0;
        header=new FAccordionHeader(this,title);
        add(header,c);
        c.gridy=1;
        add(panel,c);
        panel.setLayout(new GridBagLayout());
        header.addMouseClickListener(e -> {if(e.getButton()!=MouseEvent.BUTTON1)return; Switch();});
    }
    public void Switch()
    {
        panel.setVisible(!panel.isVisible());
        int ik=0;
    }

    public void addButton(FButton button)
    {
        GridBagConstraints c=new GridBagConstraints();
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.weightx=1;c.weighty=0;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridy=panel.getComponentCount();
        panel.add(button,c);
    }

    private class FAccordionHeader extends FComponent
    {
        @ConstructorProperties({"text"})
        public FAccordionHeader(final FAccordion owner,final String text) {

            addPaintJob(1,g -> {
                g.setColor(UIManager.getColor(owner,"color.text.normal"));
                g.setFont(UIManager.getFont(owner,"font.header.bold"));
                FontMetrics metrics= g.getFontMetrics();
                Rectangle b=getBounds();
                g.drawString(text,g.getFont().getSize(),(b.height-metrics.getHeight())/2+metrics.getAscent());
                Graphics2D gg= (Graphics2D)g;

                gg.rotate(-Math.PI/2);
                gg.drawString(panel.isVisible()?'\u203A'+""+'\u203A':'\u2039'+""+'\u2039',-(b.height-metrics.getHeight())/2-metrics.getAscent(),b.width-20);

            });
            setAbsoluteSize(150,UIManager.getFont(owner,"font.header.bold").getSize()*3);
        }
    }
}
