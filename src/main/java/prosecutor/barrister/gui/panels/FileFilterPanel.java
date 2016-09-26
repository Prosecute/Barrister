package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.forms.FileFilterDialog;
import prosecutor.barrister.jaxb.FileFilter;
import prosecutor.barrister.jaxb.TrialConfiguration;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

import static prosecutor.barrister.gui.ProjectFrame.*;

public class FileFilterPanel extends JPanel {

    private final TrialConfiguration.FileFilters filters;

    public FileFilterPanel(TrialConfiguration.FileFilters filters)
    {
        this.filters=filters;
        prepareUI();
        refresh();
    }
    public void addListSelectionListener(ListSelectionListener listener)
    {
        DefaultListSelectionModel selectionModel=(DefaultListSelectionModel)table.getSelectionModel();
        selectionModel.addListSelectionListener(listener);
    }

    public void refresh()
    {
        DefaultTableModel model= (DefaultTableModel) table.getModel();
        for(int i=0;i<model.getRowCount();i++)
            model.removeRow(0);
        synchronized (filters) {
            for (int i = 0; i <filters.getFileFilter().size();i++)
                model.addRow(new Object[]{i+"",filters.getFileFilter().get(i).getName(),filters.getFileFilter().get(i).getMode().value()});
        }
    }
    JTable table;
    private void prepareUI()
    {
        setLayout(new BorderLayout());
        JPanel sidePanel=new JPanel();
      table = new JTable(new DefaultTableModel(new Object[]{
                R().getString("ID"),
                R().getString("Name"),
                R().getString("Mode")
        },0));


        sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
        JButton buttonAdd = new JButton(getIconIO("oxygen/16x16/list-add.png"));
        buttonAdd.addActionListener(e -> {
            FileFilterDialog dialog=new FileFilterDialog(null,SwingUtilities.getWindowAncestor(this));
            int result=dialog.showDialog();
            if(result==FileFilterDialog.RESULT_OK)
            {
                filters.getFileFilter().add(dialog.FileFilter);
                refresh();
            }
        });
        JButton buttonConfigure = new JButton(getIconIO("oxygen/16x16/configure.png"));
        buttonConfigure.addActionListener(e -> {
            int selectedRow=table.getSelectedRow();
            if(selectedRow<0) return;
            FileFilter fileFilter=filters.getFileFilter().get(selectedRow);
            FileFilterDialog dialog=new FileFilterDialog(fileFilter,SwingUtilities.getWindowAncestor(this));
            int result=dialog.showDialog();
            refresh();
        });
        JButton buttonRemove = new JButton(getIconIO("oxygen/16x16/list-remove.png"));
        buttonRemove.addActionListener(e -> {
            int selectedRow=table.getSelectedRow();
            if(selectedRow<0) return;
            filters.getFileFilter().remove(selectedRow);
            refresh();
        });
        sidePanel.add(buttonAdd);
        sidePanel.add(buttonConfigure);
        sidePanel.add(buttonRemove);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);
        add(sidePanel, BorderLayout.AFTER_LINE_ENDS);
    }

}
