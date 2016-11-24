package prosecutor.barrister.gui.layouts;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.RibbonHelper;
import prosecutor.barrister.gui.components.FComponent;

public class MarginBox<T> {
    public volatile Integer LEFT,TOP,RIGHT,BOTTOM;
    protected T parrent;

    public MarginBox()
    {
        LEFT=TOP=RIGHT=BOTTOM=null;
    }
    public MarginBox(Integer margin)
    {
        LEFT=TOP=RIGHT=BOTTOM=margin;
    }
    public MarginBox(Integer horizontal,Integer vertical)
    {
        LEFT=RIGHT=horizontal;
        TOP=BOTTOM=vertical;
    }
    public MarginBox(Integer left,Integer top,Integer right,Integer bottom)
    {
        LEFT=left; TOP=top; RIGHT=right; BOTTOM=bottom;
    }

    public MarginBox setAll(Integer value)
    {
        LEFT=TOP=RIGHT=BOTTOM=value;
        return this;
    }
    public MarginBox setLeft(Integer value)
    {
        LEFT=value;
        return this;
    }
    public MarginBox setRight(Integer value)
    {
        RIGHT=value;
        return this;
    }
    public MarginBox setTop(Integer value)
    {
        TOP=value;
        return this;
    }
    public MarginBox setBottom(Integer value)
    {
        BOTTOM=value;
        return this;
    }

    public T back()
    {
        return parrent;
    }


    public T copy(MarginBox margin) {
        this.LEFT=margin.LEFT;
        this.TOP=margin.TOP;
        this.RIGHT=margin.RIGHT;
        this.BOTTOM=margin.BOTTOM;
        return back();
    }
}
