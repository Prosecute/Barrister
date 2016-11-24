package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.Align;
import prosecutor.barrister.gui.managers.UIManager;

import java.awt.*;

public class FLabel<T extends FLabel> extends FComponent<T> {

    protected StringBuffer Text;
    protected Align Align= prosecutor.barrister.gui.Align.LEFT;
    protected String FontR="font.text.normal";
    protected String ColorR="color.text.normal";

    public FLabel(String text)
    {
        Text=new StringBuffer(text);
    }
    public FLabel (final StringBuffer text)
    {
        Text=text;
    }

    public T setAlign(Align align)
    {
        this.Align=align;
        return getThis();
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(UIManager.getColor(this,ColorR));
        g.setFont(UIManager.getFont(this,FontR));
        Rectangle b= getBounds();
        FontMetrics metrics= g.getFontMetrics();
        switch (Align)
        {
            case LEFT:
                g.drawString(Text.toString(),0,(b.height-metrics.getHeight())/2+metrics.getAscent());
                break;
            case CENTER:
                g.drawString(Text.toString(),(b.width-metrics.stringWidth(Text.toString()))/2,(b.height-metrics.getHeight())/2+metrics.getAscent());
                break;
            case RIGHT:
                g.drawString(Text.toString(),b.width-metrics.stringWidth(Text.toString()),(b.height-metrics.getHeight())/2+metrics.getAscent());
                break;
        }
        g.dispose();
    }
}
