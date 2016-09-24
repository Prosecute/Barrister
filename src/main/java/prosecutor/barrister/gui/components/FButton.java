package prosecutor.barrister.gui.components;
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
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FButton extends JButton{

    public FButton(String title,Icon icon)
    {
        super(title,icon);
        putClientProperty(SubstanceLookAndFeel.COLORIZATION_FACTOR, 1.0);
        FButton button=this;
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255,255,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0,0,0));
            }
        });
    }
}
