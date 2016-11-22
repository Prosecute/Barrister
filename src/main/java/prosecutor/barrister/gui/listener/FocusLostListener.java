package prosecutor.barrister.gui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Fry on 22.11.2016.
 */
public interface FocusLostListener extends FocusListener {

    @Override
    public default void focusGained(FocusEvent e){}
}
