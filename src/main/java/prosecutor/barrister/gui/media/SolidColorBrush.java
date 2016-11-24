package prosecutor.barrister.gui.media;

import java.awt.*;

/**
 * Created by Fry on 24.11.2016.
 */
public class SolidColorBrush extends Brush {

    public volatile Color Color;

    public SolidColorBrush(String hex)
    {
        if(hex.length()!=7 &&hex.length()!=9)
            throw new RuntimeException(); //TODO Exception add
        if(hex.charAt(0)!='#')
            throw new RuntimeException(); //TODO Exception add
        if(hex.length()==7)
            this.Color = new Color(Integer.valueOf(hex.substring(1,3),16),
                Integer.valueOf(hex.substring(3,5),16),
                Integer.valueOf(hex.substring(5,7),16));
        else
            this.Color = new Color(Integer.valueOf(hex.substring(1,3),16),
                    Integer.valueOf(hex.substring(3,5),16),
                    Integer.valueOf(hex.substring(5,7),16),
                    Integer.valueOf(hex.substring(7,9),16));

    }
    public SolidColorBrush(int red,int green,int blue)
    {
        this(new Color(red,green,blue));
    }
    public SolidColorBrush(Color color)
    {
        this.Color=color;
    }

}
