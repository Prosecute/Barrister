package prosecutor.barrister.gui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Fry on 22.11.2016.
 */
public interface FocusGainedListener extends FocusListener{

    @Override
    public default void focusLost(FocusEvent e){}
}
