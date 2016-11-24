package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.managers.*;
import prosecutor.barrister.gui.managers.UIManager;

import javax.swing.*;
import java.awt.*;

public class Form extends JFrame {

    public Form()
    {
        Color c=UIManager.getColor("color.form.bg");
        getContentPane().setBackground(c);
        setBackground(c);
    }

}
