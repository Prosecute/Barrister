package prosecutor.barrister.gui.graphics;

/**
 * Created by Fry on 18.11.2016.
 */
public class ChartData {

    public String Name;
    public String Description;
    public Double Value;
    public java.awt.Color Color;

    public ChartData(Double value,java.awt.Color color)
    {
        this.Value=value;
        this.Color=color;
    }
    public ChartData(String name,Double value,java.awt.Color color)
    {
        this(value,color);
        this.Name=name;
    }
    public ChartData(String name,Double value,java.awt.Color color,String description)
    {
        this(name,value,color);
        this.Description=description;
    }
}
