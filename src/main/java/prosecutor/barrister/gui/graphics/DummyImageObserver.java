package prosecutor.barrister.gui.graphics;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by Fry on 24.11.2016.
 */
public class DummyImageObserver implements ImageObserver {
    private DummyImageObserver()
    {

    }
    private static DummyImageObserver ins;
    public static DummyImageObserver Instance()
    {
        if(ins==null)
            ins=new DummyImageObserver();
        return ins;
    }
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
