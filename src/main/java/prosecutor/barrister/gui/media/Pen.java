package prosecutor.barrister.gui.media;

/**
 * Created by Fry on 24.11.2016.
 */
public class Pen {

    public volatile int Thickness;
    public volatile Brush Brush;

    public Pen(Brush brush,Integer thickness)
    {
        if(thickness==null)
            this.Thickness=1;
        else
            this.Thickness=thickness;
        this.Brush=brush;
    }
}
