package prosecutor.barrister.gui;


import prosecutor.barrister.gui.geom.Size;
import prosecutor.barrister.gui.layouts.MarginBox;
import prosecutor.barrister.gui.media.Brush;

import javax.swing.*;
import java.awt.*;

public abstract class UIElement<T extends UIElement> {

    public volatile boolean Focusable = false;

    private volatile boolean Focused = false;
    private volatile boolean Visible = true;
    private volatile float Opacity = 1.0f;
    private volatile Brush OpacityMask = null;
    private volatile Size RenderSize = null;
    private volatile Cursor Cursor = java.awt.Cursor.getDefaultCursor();

    private volatile float Width,Height,MinWidth,MinHeight,MaxWidth,MaxHeight;
    private final MarginBox<UIElement<T>> marginBox = new MarginBox<>();

    public T setFocused(boolean focus)
    {
        this.Focused=focus;
        return getThis();
    }
    public boolean isFocused()
    {
        return this.Focused;
    }

    public T setVisible(boolean visibility)
    {
        this.Visible=visibility;
        return getThis();
    }
    public boolean isVisible()
    {
        return this.Visible;
    }



    public T getThis()
    {
        return (T)this;
    }


}
