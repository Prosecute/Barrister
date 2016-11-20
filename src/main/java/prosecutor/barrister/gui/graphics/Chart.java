package prosecutor.barrister.gui.graphics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fry on 18.11.2016.
 */
public abstract class Chart extends JComponent{

    protected List<ChartData> dataList=new ArrayList();
    protected boolean showDescription=false;
    protected DescriptionPosition descriptionPosition=DescriptionPosition.right;

    public void add(int position,ChartData data)
    {
        dataList.add(position,data);
        invalidate();
    }
    public void add(ChartData data)
    {
        dataList.add(data);
        invalidate();
    }
    public void remove(ChartData data)
    {
        dataList.remove(data);
        invalidate();
    }
    public void remove(int position)
    {
        dataList.remove(position);
        invalidate();
    }
    public void showDescription(boolean value)
    {
        this.showDescription=value;
    }
    public boolean isDescriptionVisible()
    {
        return this.showDescription;
    }
    public void setDescriptionPosition(DescriptionPosition position)
    {
        this.descriptionPosition=position;
    }
    public DescriptionPosition getDescriptionPosition()
    {
        return this.descriptionPosition;
    }

    public enum DescriptionPosition {left,right, top, bottom}
}
