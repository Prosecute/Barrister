package prosecutor.barrister.gui.listener;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MouseClickListener extends MouseListener {
    @Override
    public default void mousePressed(MouseEvent e) { }

    @Override
    public default void mouseReleased(MouseEvent e) {}

    @Override
    public default void mouseEntered(MouseEvent e) {}

    @Override
    public default void mouseExited(MouseEvent e) {}
}
