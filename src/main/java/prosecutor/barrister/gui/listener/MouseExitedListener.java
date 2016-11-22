package prosecutor.barrister.gui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Fry on 22.11.2016.
 */
public interface MouseExitedListener extends MouseListener {

    @Override
    public default void mousePressed(MouseEvent e) { }

    @Override
    public default void mouseReleased(MouseEvent e) {}

    @Override
    public default void mouseEntered(MouseEvent e) {}

    @Override
    public default void mouseClicked(MouseEvent e) {}
}
