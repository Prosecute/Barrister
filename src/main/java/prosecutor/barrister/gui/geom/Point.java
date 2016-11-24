package prosecutor.barrister.gui.geom;

/**
 * Created by Fry on 24.11.2016.
 */
public class Point {
    public volatile float X;
    public volatile float Y;

    public Point()
    {
        this(0,0);
    }
    public Point(float x, float y)
    {
        this.X=x;
        this.Y=y;
    }
}
