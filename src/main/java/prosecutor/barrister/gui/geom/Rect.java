package prosecutor.barrister.gui.geom;

/**
 * Created by Fry on 24.11.2016.
 */
public class Rect {
    public volatile Point A;
    public volatile float Width,Height;

    public Rect(float x,float y,float width,float height)
    {
        A=new Point(x,y);
        Width=width;
        Height=height;
    }


    public float X()
    {
        return A.X;
    }
    public float Y()
    {
        return A.Y;
    }

    public Rect(Point a, Point b)
    {
        A=a;
        Width=b.X-a.X;
        Height=b.Y-a.Y;
    }
}
