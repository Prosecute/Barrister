package prosecutor.barrister.gui.layouts;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.awt.*;
import java.util.*;
import java.util.List;

public class MarginLayout implements LayoutManager3 {
    protected List<Marginable> components=new ArrayList<>();


    @Override
    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            components.remove((Marginable) comp);
        }
    }



    @Override
    public void layoutContainer(Container parent) {
        Dimension size=parent.getSize();
        for(Marginable marginable:components)
        {
            marginable.setBounds(marginable.getEnv().getBounds(size));
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        synchronized (comp.getTreeLock()){
            components.add((Marginable)comp);
        }
    }

}
