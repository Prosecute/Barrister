package prosecutor.barrister.gui.geom;

/**
 * Created by Fry on 24.11.2016.
 */
public class Size {
    public volatile float X;
    public volatile float Y;

    public Size()
    {
        this(0,0);
    }
    public Size(float x, float y)
    {
        this.X=x;
        this.Y=y;
    }

}
