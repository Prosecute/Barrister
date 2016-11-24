package prosecutor.barrister.gui.layouts;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.components.FComponent;

import java.awt.*;

public interface Marginable<T extends FComponent>{

    Enviroment<T> getEnv();

    void setBounds(int x, int y, int width, int height);

    default void setBounds(Rectangle bounds)
    {
        setBounds(bounds.x,bounds.y,bounds.width,bounds.height);
    }
}
