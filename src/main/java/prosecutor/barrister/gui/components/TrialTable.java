package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import static prosecutor.barrister.gui.ProjectFrame.R;

public class TrialTable extends JTable {

    public TrialTable()
    {
        super(new DefaultTableModel(new Object[]{R().getString("TrialsID"),R().getString("TrialsName"),
                R().getString("TrialsCategory"),R().getString("TrialsTypeName"),
                R().getString("TrialsMode")},0));
        setCellSelectionEnabled(false);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setFillsViewportHeight(true);
        DefaultListSelectionModel selectionModel=(DefaultListSelectionModel)getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getColumnModel().getColumn(0).setMaxWidth(50);
        getColumnModel().getColumn(0).setMinWidth(50);
        getColumnModel().getColumn(4).setMaxWidth(150);
        getColumnModel().getColumn(4).setMinWidth(150);
        getColumnModel().getColumn(3).setMaxWidth(150);
        getColumnModel().getColumn(3).setMinWidth(150);
        getColumnModel().getColumn(2).setMaxWidth(150);
        getColumnModel().getColumn(2).setMinWidth(150);
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(new Object[]{model.getRowCount(),"Source code trial - Java","Source code","Java (1.8)","Token match"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
