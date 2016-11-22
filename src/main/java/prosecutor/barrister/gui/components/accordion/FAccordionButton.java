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
import java.beans.ConstructorProperties;

public class FAccordionButton extends JPanel {


    public FAccordionButton() {

    }

    public FAccordionButton(Icon icon){
    }

    @ConstructorProperties({"text"})
    public FAccordionButton(String text) {

    }

    public FAccordionButton(Action a) {

    }

    public FAccordionButton(String text, Icon icon) { }


    @Override
    public void paint(Graphics g) {
    }
}
