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

public interface LayoutManager3 extends LayoutManager2 {

    @Override
    default Dimension minimumLayoutSize(Container parent) {
        return parent.getSize();
    }
    @Override
    default Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }
    @Override
    default void addLayoutComponent(String name, Component comp) {
    }

    @Override
    default Dimension maximumLayoutSize(Container target) {
        return target.getSize();
    }

    @Override
    default float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    default float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    default void invalidateLayout(Container target) {
    }
}
