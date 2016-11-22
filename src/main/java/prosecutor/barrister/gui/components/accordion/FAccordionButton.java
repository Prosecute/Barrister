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
import java.beans.ConstructorProperties;

public class FAccordionButton extends JButton {


    public FAccordionButton() {
        super(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public FAccordionButton(Icon icon) {
        super(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    @ConstructorProperties({"text"})
    public FAccordionButton(String text) {
        super(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public FAccordionButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public FAccordionButton(String text, Icon icon) { super(text,icon);}

}
