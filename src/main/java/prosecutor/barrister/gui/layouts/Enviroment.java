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

public class Enviroment<T extends FComponent> {
    public enum SizeToContent { Manual,Width,Height,WidthAndHeight;}

    private final T owner;
    public final MarginBox<Enviroment<T>> Margin=new MarginBox();
    public volatile Integer Width,Height;
    public volatile Integer Order;
    public volatile SizeToContent SizeToContent;

    public Enviroment(T owner)
    {
        this.owner=owner;
    }

    public T back()
    {
        return owner;
    }

    public Rectangle getBounds(Dimension parent)
    {
        int x=0,y=0,w=0,h=0;
        if(Margin.LEFT!=null && Margin.RIGHT!=null)
        {   x=Margin.LEFT;   w=parent.width-Margin.RIGHT-x;  }
        else if(Margin.LEFT!=null)
        {   x=Margin.LEFT;   w=Width==null?parent.width-x:Width;}
        else if(Margin.RIGHT!=null)
        {   x=Width==null?0:parent.width-Width-Margin.RIGHT; w=Width==null?parent.width-Margin.RIGHT:Width;}
        else
        {   x=0; w=Width==null?parent.width:Width;}

        if(Margin.TOP!=null && Margin.BOTTOM!=null)
        {   y=Margin.TOP;   h=parent.height-Margin.BOTTOM-y;  }
        else if(Margin.TOP!=null)
        {   y=Margin.TOP;   h=Height==null?parent.height-y:Height;}
        else if(Margin.BOTTOM!=null)
        {   y=Height==null?0:parent.height-Height-Margin.BOTTOM; h=Height==null?parent.height-Margin.BOTTOM:Height;}
        else
        {   y=0; h=Height==null?parent.height:Height;}

        return new Rectangle(x,y,w,h);
    }

    public Enviroment<T> setMargin(MarginBox<Enviroment<T>> margin)
    {
        if(margin.parrent!=null && margin.parrent!=this)
            throw new RuntimeException(); //TODO
        margin.parrent=this;
        this.Margin.copy(margin);
        return this;
    }
    public Enviroment<T> setMargin(Integer left,Integer top,Integer right,Integer bottom)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(left, top, right, bottom);
        return setMargin(marginBox);
    }
    public Enviroment<T> setMargin(Integer horizontal,Integer vertical)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(horizontal,vertical);
        return setMargin(marginBox);
    }
    public Enviroment<T> setMargin(Integer margin)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(margin);
        return setMargin(marginBox);
    }


}
