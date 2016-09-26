package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButtonPanel;
import org.pushingpixels.flamingo.api.common.JCommandMenuButton;
import org.pushingpixels.flamingo.api.common.popup.JCommandPopupMenu;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelManager;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonContextualTaskGroup;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.TrialTable;
import prosecutor.barrister.gui.forms.SourceCodeTrialDialog;
import prosecutor.barrister.gui.tabbedPane.CustomTabbedPaneUI;
import prosecutor.barrister.jaxb.ConfigurationType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.List;
import java.util.*;

import static prosecutor.barrister.gui.ProjectFrame.R;
import static prosecutor.barrister.gui.ProjectFrame.getResizableIconFromResource;

public class TrialsPanel extends JPanel {

    public RibbonContextualTaskGroup group;
    ConfigurationType.Trials trials;
    TrialTable table;
    public TrialsPanel(ConfigurationType.Trials trials)
    {
        this.trials=trials;
        setLayout(new GridLayout(2, 1, 10, 10));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(ProjectFrame.R().getString("Info"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("Options"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("FileFilters"), new JInfoPanel(R().getString("NoTrialSelected")));
        tabbedPane.addTab(ProjectFrame.R().getString("BaseCode"), new JPanel());
        tabbedPane.setUI(new CustomTabbedPaneUI());
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);


        JTabbedPane previewPane = new JTabbedPane();
        previewPane.addTab(ProjectFrame.R().getString("Overview"), new JPanel());
        previewPane.addTab(ProjectFrame.R().getString("Sensitivity"), new JPanel());
        previewPane.setUI(new CustomTabbedPaneUI());
        previewPane.setTabPlacement(JTabbedPane.BOTTOM);

        table=new TrialTable();
        table.addListSelectionListener(e -> {
            int i=e.getFirstIndex();
            if(i<0){
                tabbedPane.setComponentAt(2, new JInfoPanel(R().getString("NoTrialSelected")));
                return;
            }
            tabbedPane.setComponentAt(2,new FileFilterPanel(trials.getTrial().get(i).getFileFilters()));
        });
        JScrollPane JScrollPane1 = new JScrollPane(table);
        JScrollPane1.setViewportView(table);

        TitledPanel trialsPane=new TitledPanel(R().getString("Trials"),JScrollPane1);
        TitledPanel properties=new TitledPanel(R().getString("Properties"),tabbedPane);
        TitledPanel preview=new TitledPanel(R().getString("Preview"),previewPane);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));
        panel.add(properties);
        panel.add(preview);
        add(panel);
        add(trialsPane);
        setupRibbon();
    }
    public void setupRibbon()
    {
        JRibbonBand bandNew = new JRibbonBand(R().getString("New"), null);
        JRibbonBand bandActions = new JRibbonBand(R().getString("Actions"), null);
        JCommandButton buttonAddTrial = new JCommandButton(R().getString("AddTrial"), getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
        buttonAddTrial.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        buttonAddTrial.add(new JCommandButton("Test"));
        buttonAddTrial.setPopupCallback(new PopupPanelCallback() {
            @Override
            public JPopupPanel getPopupPanel(JCommandButton commandButton) {
                JCommandPopupMenu menu=new JCommandPopupMenu();
                JCommandMenuButton buttonNewSourceCode =new JCommandMenuButton(R().getString("NewSourceCodeTrial"),getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
                buttonNewSourceCode.addActionListener(e -> {
                    SourceCodeTrialDialog dialog = new SourceCodeTrialDialog(null,SwingUtilities.getWindowAncestor(getParent()));
                    int ret=dialog.showDialog();
                    if(ret==SourceCodeTrialDialog.RESULT_OK)
                    {
                        trials.getTrial().add(dialog.getConfiguration());
                    }
                });
                JCommandMenuButton buttonNewText =new JCommandMenuButton(R().getString("NewTextTrial"),getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
                JCommandMenuButton buttonNewImage =new JCommandMenuButton(R().getString("NewImageTrial"),getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
                menu.addMenuButton(buttonNewSourceCode);
                menu.addMenuButton(buttonNewText);
                menu.addMenuButton(buttonNewImage);
                return menu;
            }
        });

        JCommandButton buttonUpdateTrial = new JCommandButton(R().getString("UpdateTrial"), getResizableIconFromResource("oxygen/32x32/actions/configure.png"));
        buttonUpdateTrial.addActionListener(e1 -> {
            int i=table.getSelectedRow();
            if(i<0) return;
            SourceCodeTrialDialog dialog = new SourceCodeTrialDialog(trials.getTrial().get(i),SwingUtilities.getWindowAncestor(getParent()));
            int ret=dialog.showDialog();
        });
        JCommandButton buttonRemoveTrial = new JCommandButton(R().getString("RemoveTrial"), getResizableIconFromResource("oxygen/32x32/actions/list-remove.png"));
        buttonRemoveTrial.addActionListener(e -> {
            int i=table.getSelectedRow();
            if(i<0) return;
            trials.getTrial().remove(i);
            ((DefaultTableModel)table.getModel()).removeRow(i);
        });


        bandNew.setResizePolicies((java.util.List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandNew.getControlPanel())));
        bandActions.setResizePolicies((java.util.List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandActions.getControlPanel())));

        bandNew.addCommandButton(buttonAddTrial, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonUpdateTrial,RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonRemoveTrial,RibbonElementPriority.TOP);
        RibbonTask taskTestedFiles = new RibbonTask(ProjectFrame.R().getString("Trial"), bandNew,bandActions);
        group = new RibbonContextualTaskGroup(ProjectFrame.R().getString("Trials"), Color.yellow,
                taskTestedFiles );
    }
}
