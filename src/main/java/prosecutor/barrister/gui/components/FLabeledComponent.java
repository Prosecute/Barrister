package prosecutor.barrister.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Fry on 15.11.2016.
 */
public class FLabeledComponent<T extends JComponent> extends JPanel {

    public T component;
    public FLabeledComponent(String label, int size,T component)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.weighty=0;
        c.weightx=0;
        c.insets=new Insets(0,10,0,0);
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.fill=GridBagConstraints.HORIZONTAL;
        JLabel l=new JLabel(label);
        l.setSize(size,l.getHeight());
        l.setMaximumSize(new Dimension(size,(int)l.getMaximumSize().getHeight()));
        l.setMinimumSize(new Dimension(size,(int)l.getMinimumSize().getHeight()));
        l.setPreferredSize(new Dimension(size,(int)l.getPreferredSize().getHeight()));
        l.setVerticalAlignment(JLabel.BOTTOM);
        add(l,c);
        this.component=component;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.weightx=1;
        add(component,c);

    }
    public FLabeledComponent(String label,T component)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.weighty=0;
        c.weightx=0;
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        JLabel l=new JLabel(label);
        l.setVerticalAlignment(JLabel.BOTTOM);
        add(l,c);
        this.component=component;
        c.insets=new Insets(0,10,0,0);
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.weightx=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        add(component,c);

    }
}
