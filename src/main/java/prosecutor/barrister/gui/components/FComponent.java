package prosecutor.barrister.gui.components;

import prosecutor.barrister.gui.layouts.Enviroment;
import prosecutor.barrister.gui.layouts.Marginable;
import prosecutor.barrister.gui.listener.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fry on 22.11.2016.
 */
public class FComponent<T extends FComponent> extends JComponent implements Marginable<T>{
    protected String Style= "default";
    protected boolean overlap=false;
    private final Enviroment<T> enviroment;
    {
        setOpaque(false);
        enviroment=new Enviroment<T>((T)this);
    }

    @Override
    public boolean isOptimizedDrawingEnabled()
    {
        return !overlap;
    }

    public T allowOverlap(boolean overlap)
    {
        this.overlap=overlap;
        return getThis();
    }

    public T addMouseClickListener(MouseClickListener mouseClickListener)
    {
        addMouseListener(mouseClickListener);
        return getThis();
    }
    public T addMouseEnteredListener(MouseEnteredListener listener)
    {
        addMouseListener(listener);
        return getThis();
    }
    public T addMouseExitedListener(MouseExitedListener listener)
    {
        addMouseListener(listener);
        return getThis();
    }
    public T addFocusGainedListener(FocusGainedListener listener) {addFocusListener(listener); return getThis();}
    public T addFocusLostListener(FocusLostListener listener) {addFocusListener(listener); return getThis();}

    private HashMap<Integer,ArrayList<PaintJob>> jobs=new HashMap<>();
    public T addPaintJob(PaintJob job)
    {
        return addPaintJob(1,job);
    }
    public T addPaintJob(int layer,PaintJob job)
    {
        if(!jobs.containsKey(layer))
            jobs.put(layer,new ArrayList<>());
        jobs.get(layer).add(job);
        return getThis();
    }
    public T removePaintLayer(int layer)
    {
        jobs.remove(layer);
        return getThis();
    }
    public T getThis()
    {
        return (T)this;
    }

    public T setStyle(String newStyle)
    {
        this.Style=newStyle;
        invalidate();
        return getThis();
    }
    public String getStyle()
    {
        return Style;
    }

    public T setAbsoluteSize(int w, int h)
    {
        setAbsoluteSize(new Dimension(w,h));
        return getThis();
    }
    public T setAbsoluteSize(Dimension dim)
    {
        setSize(dim);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        return getThis();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(ArrayList<PaintJob> jobList:jobs.values())
            for(PaintJob job:jobList)
                job.paint(g);
        g.dispose();
    }

    @Override
    public Enviroment<T> getEnv() {
        return enviroment;
    }

    @Override
    public Component add(Component comp) {
        addImpl(comp, null, 0);
        return comp;
    }

    @Override
    public Component add(String name, Component comp) {
        addImpl(comp, name, 0);
        return comp;
    }
    @Override
    public Component add(Component comp, int index) {
        addImpl(comp, null, index);
        return comp;
    }
}
