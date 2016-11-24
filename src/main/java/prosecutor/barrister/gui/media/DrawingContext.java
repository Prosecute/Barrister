package prosecutor.barrister.gui.media;

import prosecutor.barrister.gui.geom.*;
import prosecutor.barrister.gui.geom.Point;
import prosecutor.barrister.gui.graphics.DummyImageObserver;

import java.awt.*;

/**
 * Created by Fry on 24.11.2016.
 */
public class DrawingContext {

    Graphics g;
    Graphics2D g2;

    protected DrawingContext()
    {}

    public static DrawingContext create(Graphics g)
    {
        DrawingContext dc=new DrawingContext();
        dc.g=g;
        if(g instanceof Graphics2D)
            dc.g2=(Graphics2D)g;
        else
            dc.g2=new DummyGraphics2D();
        return dc;
    }
    private boolean prepPen(Pen pen)
    {
        if(pen==null)
            return false;
        g2.setStroke(new BasicStroke(pen.Thickness)); //TODO Extend stroke/thickness functionality
        g.setColor(((SolidColorBrush)pen.Brush).Color); //TODO Extend brush variations
        return true;
    }
    private boolean prepBrush(Brush brush)
    {
        if(brush==null)
            return false;
        g.setColor(((SolidColorBrush)brush).Color);
        return true;
    }

    public DrawingContext DrawEllipse(Brush brush, Pen pen, Point point, float width, float height)
    {
        if(prepBrush(brush))
            g.fillOval((int)point.X,(int)point.Y,(int)width,(int)height);
        if(prepPen(pen))
            g.drawOval((int)point.X,(int)point.Y,(int)width,(int)height);
        return this;
    }

    public DrawingContext DrawRectangle(Brush brush, Pen pen, Point point, float width, float height)
    {
        if(prepBrush(brush))
            g.fillRect((int)point.X,(int)point.Y,(int)width,(int)height);
        if(prepPen(pen))
            g.drawRect((int)point.X,(int)point.Y,(int)width,(int)height);
        return this;
    }

    public DrawingContext DrawRectangle(Brush brush, Pen pen, Rect rect)
    {
        return DrawRectangle(brush,pen,rect.A,rect.Width,rect.Height);
    }

    public DrawingContext DrawText(Pen pen, FormattedText text, Point point)
    {
        g.setFont(text.Font);
        if(prepPen(pen))
            g.drawString(text.Text,(int)point.X,(int)point.Y);
        return this;
    }

    public DrawingContext DrawImage(Image image,Rect rect)
    {
        g.drawImage(image,(int)rect.X(),(int)rect.Y(),(int)rect.Width,(int)rect.Height, DummyImageObserver.Instance());
        return this;
    }
    public DrawingContext DrawImage(Brush brush, Image image, Rect rect)
    {
        if(prepBrush(brush))
            g.drawImage(image,(int)rect.X(),(int)rect.Y(),(int)rect.Width,(int)rect.Height,((SolidColorBrush)brush).Color, DummyImageObserver.Instance());
        return this;
    }




}
