package prosecutor.barrister.gui.graphics;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PieChart extends JComponent {

    private List<PieData> dataList=new ArrayList();

    public void add(int position,PieData data)
    {
        dataList.add(position,data);
        invalidate();
    }
    public void add(PieData data)
    {
        dataList.add(data);
        invalidate();
    }
    public void remove(PieData data)
    {
        dataList.remove(data);
        invalidate();
    }
    public void remove(int position)
    {
        dataList.remove(position);
        invalidate();
    }

    @Override
    public void paint(Graphics g) {
        Rectangle bounds=getBounds();
        double totalVal= 0.0D,curVal=0.0D;
        for(int i=0; i<dataList.size();i++)
            totalVal+=dataList.get(i).Value;
        for(PieData data:dataList)
        {
            int arc = (int)(data.Value *360/totalVal);
            int angle=(int)(curVal*360/totalVal);
            g.setColor(data.Color);
            g.fillArc(bounds.x,bounds.y,bounds.width,bounds.height,angle,arc);
            curVal+=data.Value;
        }

    }


    public static class PieData {
        public String Name;
        public String Description;
        public Double Value;
        public java.awt.Color Color;

        public PieData(Double value,java.awt.Color color)
        {
            this.Value=value;
            this.Color=color;
        }
        public PieData(String name,Double value,java.awt.Color color)
        {
            this(value,color);
            this.Name=name;
        }
        public PieData(String name,Double value,java.awt.Color color,String description)
        {
            this(name,value,color);
            this.Description=description;
        }
    }

}
