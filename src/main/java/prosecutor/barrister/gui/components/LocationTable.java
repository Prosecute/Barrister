package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.panels.TestedFilePanel;
import prosecutor.barrister.jaxb.EntitiesLocation;
import prosecutor.barrister.jaxb.TestMode;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigInteger;
import java.util.List;

import static prosecutor.barrister.gui.ProjectFrame.*;

public class LocationTable extends JTable{


    public LocationTable(TestedFilePanel panel)
    {
        super(new DefaultTableModel(new Object[]{R().getString("TableFilesID"),R().getString("TableFilesName"),
                R().getString("TableFilesURL"),R().getString("TableFilesMode"),
                R().getString("TableFilesDirect")},0));
        JComboBox modeBox = new JComboBox();
        modeBox.addItem("Test");
        modeBox.addItem("Compare");
        JCheckBox checkBox =new JCheckBox();
        getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(checkBox));
        getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer());
        getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(modeBox));
        getColumnModel().getColumn(0).setMaxWidth(50);
        getColumnModel().getColumn(0).setMinWidth(50);
        getColumnModel().getColumn(3).setMaxWidth(50);
        getColumnModel().getColumn(3).setMinWidth(50);
        getColumnModel().getColumn(4).setMaxWidth(100);
        getColumnModel().getColumn(4).setMinWidth(100);
        getColumnModel().getColumn(1).setWidth(100);
        setFillsViewportHeight(true);

        rebuild(ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation());

        DefaultTableModel model = (DefaultTableModel) getModel();
        setCellSelectionEnabled(false);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        DefaultListSelectionModel selectionModel=(DefaultListSelectionModel)getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if(e.getFirstIndex()<0||e.getLastIndex()>model.getRowCount())
                return;
            panel.setActive(e.getFirstIndex());
            panel.refreshRibbonState();
        });
        CellEditorListener Change = new CellEditorListener() {
            public void editingCanceled(ChangeEvent e) {
            }

            public void editingStopped(ChangeEvent e) {
                updateEntityFromLine(getSelectedRow());
            }
        };
        this.getDefaultEditor(String.class).addCellEditorListener(Change);
        this.getDefaultEditor(Boolean.class).addCellEditorListener(Change);
        modeBox.addActionListener(e ->{
                updateEntityFromLine(getSelectedRow());
        });
        checkBox.addActionListener(e -> {
                updateEntityFromLine(getSelectedRow());
        });
    }
    public void updateEntityFromLine(int i)
    {
        EntitiesLocation location=ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().get(i);
        location.setEntitiesLocationID(new BigInteger((int) this.getValueAt(i, 0) + ""));
        //TODO location.setName
        location.setPath((String)this.getValueAt(i,2));
        location.setDirect((Boolean)this.getValueAt(i,3));
        location.setMode(TestMode.fromValue((String)this.getValueAt(i,4)));
    }

    public void rebuildLine(EntitiesLocation location)
    {
        DefaultTableModel model = (DefaultTableModel) getModel();
        int pos=location.getEntitiesLocationID().intValue();
        model.removeRow(pos);
        model.insertRow(pos,entityLocationToObject(location));
    }
    public void moveLine(int from,int to)
    {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.moveRow(from,from,to);
    }
    public void removeLine(int id)
    {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.removeRow(id);
    }
    public void addNewLine()
    {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(new Object[]{model.getRowCount(),"","",true,"Test"});
    }

    public void rebuild(List<EntitiesLocation> list)
    {
        DefaultTableModel model = (DefaultTableModel) getModel();
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
        for(EntitiesLocation loc:list)
            model.addRow(entityLocationToObject(loc));
    }
    protected Object[] entityLocationToObject(EntitiesLocation loc)
    {
        return new Object[]{loc.getEntitiesLocationID(),"",loc.getPath(),new Boolean(loc.isDirect()),loc.getMode().value()};
    }

}
