package prosecutor.barrister.gui.components;

import prosecutor.barrister.gui.listener.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fry on 22.11.2016.
 */
public class FComponent extends JComponent{
    protected String Style= "default";

    public FComponent()
    {
    }


    public void addMouseClickListener(MouseClickListener mouseClickListener)
    {
        addMouseListener(mouseClickListener);
    }
    public void addMouseEnteredListener(MouseEnteredListener listener)
    {
        addMouseListener(listener);
    }
    public void addMouseExitedListener(MouseExitedListener listener)
    {
        addMouseListener(listener);
    }
    public void addFocusGainedListener(FocusGainedListener listener) {addFocusListener(listener);}
    public void addFocusLostListener(FocusLostListener listener) {addFocusListener(listener);}

    private HashMap<Integer,ArrayList<PaintJob>> jobs=new HashMap<>();
    public void addPaintJob(PaintJob job)
    {
        addPaintJob(1,job);
    }
    public void addPaintJob(int layer,PaintJob job)
    {
        if(!jobs.containsKey(layer))
            jobs.put(layer,new ArrayList<>());
        jobs.get(layer).add(job);
    }
    public void removePaintLayer(int layer)
    {
        jobs.remove(layer);
    }

    public void setStyle(String newStyle)
    {
        this.Style=newStyle;
        invalidate();
    }
    public String getStyle()
    {
        return Style;
    }

    public void setAbsoluteSize(int w, int h)
    {
        setAbsoluteSize(new Dimension(w,h));
    }
    public void setAbsoluteSize(Dimension dim)
    {
        setSize(dim);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(ArrayList<PaintJob> jobList:jobs.values())
            for(PaintJob job:jobList)
                job.paint(g);
        g.dispose();
    }
}
