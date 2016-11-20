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

public class PieChart extends Chart {


    @Override
    public void paint(Graphics g) {
        Rectangle bounds=getBounds();
        if(!this.showDescription)
            paintPie(g,bounds);
        else
        {

        }

    }

    private void paintPie(Graphics g, Rectangle bounds)
    {
        double totalVal= 0.0D,curVal=0.0D;
        for(int i=0; i<dataList.size();i++)
            totalVal+=dataList.get(i).Value;
        for(ChartData data:dataList)
        {
            int arc = (int)(data.Value *360/totalVal);
            int angle=(int)(curVal*360/totalVal);
            g.setColor(data.Color);
            g.fillArc(bounds.x,bounds.y,bounds.width,bounds.height,angle,arc);
            curVal+=data.Value;
        }
    }



}
