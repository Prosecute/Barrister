package prosecutor.barrister.gui.components.buttons;

import prosecutor.barrister.gui.components.FComponent;
import prosecutor.barrister.gui.managers.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.ImageObserver;


public class FButton extends FComponent {

    protected String Text;
    protected Font font;
    protected ImageIcon icon;
    protected boolean Hovered;
    protected boolean Active;


    public FButton(){this("",null);}
    public FButton(String text) {this(text,null);}
    public FButton(String text,ImageIcon icon) {
        Text=text;
        this.icon=icon;
        setFocusable(true);
        addMouseEnteredListener(e -> { Hovered=true; repaint();});
        addMouseExitedListener(e-> {Hovered=false; repaint();});
        addMouseClickListener(e -> {requestFocus(); repaint();});
        addFocusLostListener(e -> { repaint();});
        setButtonStyle(ButtonStyle.LIGHT);
        setButtonAlign(ButtonAlign.LEFT);
        setPreferredSize(new Dimension(150,150));
        setMinimumSize(new Dimension(150,150));
        setMaximumSize(new Dimension(150,150));
        font=UIManager.getFont(this,"font.text.normal");
        setAbsoluteSize(150,font.getSize()*3);

    }


    public FButton setButtonAlign(ButtonAlign align)
    {
        switch (align)
        {
            case TOP:
            case BOTTOM:
                removePaintLayer(1);
                addPaintJob(1,g -> {
                    g.setColor(UIManager.getColor(this,"color.text.normal"));
                    g.setFont(font);
                    Rectangle b= getBounds();
                    FontMetrics metrics= g.getFontMetrics();
                    g.drawString(Text,(b.width-metrics.stringWidth(Text))/2,(b.height-metrics.getHeight())/2+metrics.getAscent());
                });
                break;
            case LEFT:
            case RIGHT:
                removePaintLayer(1);
                addPaintJob(1,g -> {
                    g.setColor(UIManager.getColor(this,"color.text.normal"));
                    g.setFont(font);
                    Rectangle b= getBounds();
                    FontMetrics metrics= g.getFontMetrics();
                    g.drawString(Text,font.getSize()*2*(icon==null?1:2),(b.height-metrics.getHeight())/2+metrics.getAscent());
                    if(icon!=null) {
                        int i=font.getSize()/2,e=font.getSize()*2;
                        g.drawImage(icon.getImage(), i,i,e,e, icon.getImageObserver());
                    }

                });
                break;
        }
        return this;
    }

    public FButton setButtonStyle(ButtonStyle style)
    {
        switch (style)
        {
            case LIGHT:
                removePaintLayer(0);
                addPaintJob(0,g -> {
                    Rectangle bounds=getBounds();
                    g.setColor(UIManager.getColor(this,"color.hover"));
                    if(Hovered)
                        g.drawRect(bounds.x+1,bounds.y+1,bounds.width-2,bounds.height-2);
                    g.setColor(UIManager.getColor(this,"color.active"));
                    if(hasFocus())
                        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
                });
                break;
            case HEAVY:
                removePaintLayer(0);
                addPaintJob(0,g -> {
                    Rectangle bounds=getBounds();
                    g.setColor(UIManager.getColor(this,"color.hover"));
                    if(Hovered)
                        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
                    g.setColor(UIManager.getColor(this,"color.active"));
                    if(hasFocus())
                        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
                });
                break;
        }
        return this;
    }


    public enum ButtonStyle {
        LIGHT,HEAVY
    }

    public enum ButtonAlign {
        TOP,LEFT,BOTTOM,RIGHT
    }
}
