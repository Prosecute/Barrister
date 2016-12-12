package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.components.panels.FPanel;
import prosecutor.barrister.gui.layouts.MarginLayout;

import javax.swing.*;
import java.awt.*;

public class MarginLayoutTest {

    public static void main(String... args)
    {
        Form form=new Form();
        FPanel panel=new FPanel();

        panel.setLayout(new MarginLayout());
        panel.allowOverlap(true);
        panel.add(new FComponent()
                .getEnv()
                    .setMargin(10,10,10,10)
                .back()
                .addPaintJob(g -> {
                    g.setColor(Color.black);
                    g.fillRect(0,0,9000,9000);
                }));/*
        panel.add(new FComponent()
                .getEnv()
                .setMargin(10,10,null,null)
                .setWidth(60)
                .setHeight(150)
                .back()
                .addPaintJob(g -> {
                    g.setColor(Color.blue);
                    g.fillRect(0,0,9000,9000);
                }));*/
        form.setBounds(250,250,400,400);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.getContentPane().add(panel);
        form.setVisible(true);
    }
}
