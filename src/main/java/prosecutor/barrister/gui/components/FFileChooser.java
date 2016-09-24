package prosecutor.barrister.gui.components;
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

public class FFileChooser extends JPanel {

    public FFileChooser()
    {
        setLayout(new BorderLayout());

        JTextField field = new JTextField();

        JButton button = new JButton("...");

        add(field,BorderLayout.CENTER);
        add(button,BorderLayout.LINE_END);
    }
}
