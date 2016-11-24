package prosecutor.barrister.gui.layouts;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.awt.*;

public class RelativeMarginLayout extends MarginLayout{

    private Orientation orientation;

    public RelativeMarginLayout()
    {
        this(Orientation.BOTH);
    }
    public RelativeMarginLayout(Orientation orientation)
    {
        this.orientation=orientation;
    }



    @Override
    public void layoutContainer(Container parent) {
        Dimension size=parent.getSize();
        int x=0,y=0;
        for(Marginable marginable:components)
        {
            Rectangle rectangle=marginable.getEnv().getBounds(size);
            if(orientation!=Orientation.VERTICAL) {
                rectangle.x += x;
                x = rectangle.width+rectangle.x;
            }
            if(orientation!=Orientation.HORIZONTAL) {
                rectangle.y += y;
                y = rectangle.height+rectangle.y;
            }
            marginable.setBounds(rectangle);
        }
    }

    public enum Orientation { HORIZONTAL,VERTICAL, BOTH}
}
