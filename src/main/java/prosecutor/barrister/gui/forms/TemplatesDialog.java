package prosecutor.barrister.gui.forms;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Enumeration;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class TemplatesDialog extends JDialog {

    public TemplatesDialog(Frame owner, String title, boolean modal)
    {
        super(owner,title,modal);
        Dimension dimension=new Dimension(620,480);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setSize(dimension);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(R().getString("TemplateRoot"));
        DefaultMutableTreeNode def = new DefaultMutableTreeNode(R().getString("TemplateDefault"));
        DefaultMutableTreeNode sc = new DefaultMutableTreeNode(R().getString("TemplateSC"));
        DefaultMutableTreeNode scj = new DefaultMutableTreeNode(R().getString("TemplateSCJ"));
        DefaultMutableTreeNode scc = new DefaultMutableTreeNode(R().getString("TemplateSCC"));
        top.add(def);
        top.add(sc);
        sc.add(scj);
        sc.add(scc);
        scj.add(new Leaf() {{Name=R().getString("Tmp_JavaDefaultName");}});

        JTree jTree=new JTree(top);
        jTree.setCellRenderer(new CustomTreeCellRenderer(new ImageIcon(getImageIO("oxygen/16x16/documentation.png")),
                new ImageIcon(getImageIO("oxygen/16x16/folder.png"))));
        dimension=new Dimension(240,400);
        JScrollPane jsp=new JScrollPane(jTree);
        jsp.setSize(dimension);
        jsp.setBackground(Color.white);
        jsp.setMaximumSize(dimension);
        jsp.setMinimumSize(dimension);
        jsp.setPreferredSize(dimension);
        JPanel jPanel=new JPanel();
        dimension=new Dimension(380,400);
        JPanel description=new JPanel();
        description.setSize(dimension);
        description.setMaximumSize(dimension);
        description.setMinimumSize(dimension);
        JPanel bottom=new JPanel();
        bottom.setSize(new Dimension(480,80));


        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        jPanel.add(jsp);
        jPanel.add(description);


        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonOK=new JButton(R().getString("OK"));
        JButton buttonCancel=new JButton(R().getString("Cancel"));
        bottom.add(buttonOK);
        bottom.add(buttonCancel);

        setLayout(new BorderLayout());
        add(bottom,BorderLayout.AFTER_LAST_LINE);
        add(jPanel,BorderLayout.CENTER);

    }

    protected static class Leaf extends DefaultMutableTreeNode
    {
        public String Name;
        public String Description;
        @Override
        public String toString() {
            return Name;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }
    class CustomTreeCellRenderer extends DefaultTreeCellRenderer {


        ImageIcon rendererIcon,rendererIconFolder;


        public CustomTreeCellRenderer(ImageIcon rendererIcon,ImageIcon rendererIconFolder)
        {
            this.rendererIcon=rendererIcon;
            this.rendererIconFolder=rendererIconFolder;
        }


        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus){

            super.getTreeCellRendererComponent(tree, value,
                    selected, expanded, leaf, row, hasFocus);

            JLabel label = (JLabel) this ;

            if(value instanceof Leaf)
                label.setIcon( rendererIcon ) ;
            else
                label.setIcon(rendererIconFolder);


            return this;
        }
    }

}
