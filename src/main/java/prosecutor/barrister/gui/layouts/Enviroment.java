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

    private final T owner;
    private MarginBox marginBox=new MarginBox();
    private Integer width,height;

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
        if(marginBox.LEFT!=null && marginBox.RIGHT!=null)
        {   x=marginBox.LEFT;   w=parent.width-marginBox.RIGHT-x;  }
        else if(marginBox.LEFT!=null)
        {   x=marginBox.LEFT;   w=width==null?parent.width-x:width;}
        else if(marginBox.RIGHT!=null)
        {   x=width==null?0:parent.width-width-marginBox.RIGHT; w=width==null?parent.width-marginBox.RIGHT:width;}
        else
        {   x=0; w=width==null?parent.width:width;}

        if(marginBox.TOP!=null && marginBox.BOTTOM!=null)
        {   y=marginBox.TOP;   h=parent.height-marginBox.BOTTOM-y;  }
        else if(marginBox.TOP!=null)
        {   y=marginBox.TOP;   h=height==null?parent.height-y:height;}
        else if(marginBox.BOTTOM!=null)
        {   y=height==null?0:parent.height-height-marginBox.BOTTOM; h=height==null?parent.height-marginBox.BOTTOM:height;}
        else
        {   y=0; h=height==null?parent.height:height;}

        return new Rectangle(x,y,w,h);
    }

    public MarginBox<Enviroment<T>> getMargin()
    {
        return marginBox;
    }
    public Enviroment<T> setMargin(MarginBox<Enviroment<T>> margin)
    {
        if(margin.parrent!=null && margin.parrent!=this)
            throw new RuntimeException(); //TODO
        margin.parrent=this;
        this.marginBox=margin;
        return this;
    }
    public Enviroment<T> setMargin(Integer left,Integer top,Integer right,Integer bottom)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(left, top, right, bottom);
        marginBox.parrent=this;
        this.marginBox=marginBox;
        return this;
    }
    public Enviroment<T> setMargin(Integer horizontal,Integer vertical)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(horizontal,vertical);
        marginBox.parrent=this;
        this.marginBox=marginBox;
        return this;
    }
    public Enviroment<T> setMargin(Integer margin)
    {
        MarginBox<Enviroment<T>> marginBox = new MarginBox<>(margin);
        marginBox.parrent=this;
        this.marginBox=marginBox;
        return this;
    }
    public Enviroment<T> setWidth(Integer width)
    {
        this.width=width;
        return this;
    }

    public Enviroment<T> setHeight(Integer height)
    {
        this.height=height;
        return this;
    }


}
