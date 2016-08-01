package prosecutor.barrister.gui.input;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.EntitiesLocation;
import prosecutor.barrister.jaxb.TestMode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BarristerForm {

    private JPanel MainJPanel;
    private JMenuBar Menu;
    private JTabbedPane tabbedPane1;
    private JTable tableLocation;
    private JButton newLocation;
    private JButton removeAllLocation;
    private JButton removeLocation;
    private JScrollPane locationTablePanel;
    private JTable table2;
    private JButton novýButton;
    private JButton odebratVšeButton1;
    private JButton odebratButton;
    private JTextField tbLocationURL;
    private JComboBox cbDirect;
    private JButton btnLocation;
    private JComboBox cbMode;
    private JList includeList;
    private JButton btnIncludePlus;
    private JButton btnIncludeMinus;
    private JList excludeList;
    private JButton btnExcludePlus;
    private JButton btnExcludeMinus;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField2;
    private JSpinner spinner1;
    private JTextField textField3;
    private JButton chooseButton;
    private JTextField textField4;
    private JPanel panelLocationDetail;


    protected Configuration conf=new Configuration();

    public void init()
    {
        conf=new Configuration();
        JMenu menu;
        JMenuItem menuItem;
        menu = new JMenu("Soubor");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "");
        Menu.add(menu);

        menuItem = new JMenuItem("Otevřít",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        menu.add(new JPopupMenu.Separator());
        menuItem = new JMenuItem("Uložit",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        menuItem = new JMenuItem("Spustit",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        menu.add(new JPopupMenu.Separator());
        menuItem = new JMenuItem("Zavřít",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        tabbedPane1.setTitleAt(0,"Testované soubory");
        tabbedPane1.setTitleAt(1,"Testy");
        tabbedPane1.setTitleAt(2,"Ostatní");
        DefaultTableModel model = (DefaultTableModel) tableLocation.getModel();
        model.addColumn("ID");
        model.addColumn("URL");
        model.addColumn("Mód");
        model.addColumn("Subjektů");
        tableLocation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLocation.setColumnSelectionAllowed(false);
        tableLocation.setCellSelectionEnabled(false);
        tableLocation.setRowSelectionAllowed(true);

        tbLocationURL.setEnabled(false);
        panelLocationDetail.setVisible(false);

        eventRegister();

    }

    protected void eventRegister()
    {
        newLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EntitiesLocation loc=new EntitiesLocation();
                loc.setMode(TestMode.TEST_ONLY);
                loc.setDirect(false);
                loc.setPath("/");
                if(conf.getEntitiesLocations()==null)
                    conf.setEntitiesLocations(new Configuration.EntitiesLocations());
                conf.getEntitiesLocations().getEntitiesLocation().add(loc);
                showDetailLocation(loc);
                refreshLocationTable();
            }
        });
        removeLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableLocation.getSelectedRow()!=-1)
                {
                    DefaultTableModel model = (DefaultTableModel) tableLocation.getModel();
                    Integer i=(Integer) model.getValueAt(tableLocation.getSelectedRow(),0);
                    conf.getEntitiesLocations().getEntitiesLocation().remove(i.intValue());
                    refreshLocationTable();
                    i=i==0?model.getRowCount()>0?0:-1:i-1;
                    if(i!=-1)
                        tableLocation.setRowSelectionInterval(i,i);
                    else
                        panelLocationDetail.setVisible(false);
                }
            }
        });
        removeAllLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conf.getEntitiesLocations().getEntitiesLocation().clear();
                panelLocationDetail.setVisible(false);
                refreshLocationTable();
            }
        });
    }
    protected void showDetailLocation(EntitiesLocation location)
    {
        if(!panelLocationDetail.isVisible())
            panelLocationDetail.setVisible(true);

        tbLocationURL.setText(location.getPath());
        cbMode.setSelectedIndex(location.getMode()==TestMode.TEST_ONLY?0:1);
        cbDirect.setSelectedIndex(location.isDirect()==false?0:1);
    }


    protected void refreshLocationTable()
    {
        int i=0;
        DefaultTableModel model = (DefaultTableModel) tableLocation.getModel();
        model.setRowCount(0);
        for(EntitiesLocation location:conf.getEntitiesLocations().getEntitiesLocation())
        {
            model.addRow(new Object[]{i,location.getPath(),location.getMode(),"?"});
            i++;
        }
    }

    public static void main(String... args)
    {
        BarristerForm form=new BarristerForm();

        form.init();
        JFrame frame=new JFrame();
        frame.setTitle("Barrister");
        frame.setSize(new Dimension(800,600));
        frame.setMinimumSize(new Dimension(450,400));
        frame.setPreferredSize(new Dimension(800,600));
        frame.setContentPane(form.MainJPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = frame.getSize();
        int windowX = Math.max(0, (screenSize.width/2  - windowSize.width/2 ));
        int windowY = Math.max(0, (screenSize.height/2 - windowSize.height/2));
        frame.setLocation(windowX, windowY);

        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        tableLocation = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
    }


    public class LocationChangeListener implements ActionListener {

        BarristerForm form;

        public LocationChangeListener(BarristerForm form)
        {
            this.form=form;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) tableLocation.getModel();
            int i=((Integer)model.getValueAt(form.tableLocation.getSelectedRow(),0)).intValue();
            EntitiesLocation location=form.conf.getEntitiesLocations().getEntitiesLocation().get(i);
            location.setMode(cbMode.getSelectedIndex()==0?TestMode.TEST_ONLY:TestMode.COMPARE_ONLY);
            location.setDirect(cbDirect.getSelectedIndex()==0?false:true);
            location.setPath(tbLocationURL.getText());
            

        }
    }
}
