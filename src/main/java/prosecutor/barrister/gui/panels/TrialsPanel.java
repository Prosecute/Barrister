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
import org.pushingpixels.flamingo.internal.ui.common.popup.PopupPanelUI;
import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.TrialTable;
import prosecutor.barrister.gui.tabbedPane.CustomTabbedPaneUI;

import javax.swing.*;

import java.awt.*;
import java.awt.List;
import java.util.*;

import static prosecutor.barrister.gui.ProjectFrame.R;
import static prosecutor.barrister.gui.ProjectFrame.getResizableIconFromResource;

public class TrialsPanel extends JPanel {

    public RibbonContextualTaskGroup group;

    public TrialsPanel()
    {
        setLayout(new GridLayout(2, 1, 10, 10));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(ProjectFrame.R().getString("Info"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("Options"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("Extensions"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("BaseCode"), new JPanel());
        tabbedPane.setUI(new CustomTabbedPaneUI());
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);


        JTabbedPane previewPane = new JTabbedPane();
        previewPane.addTab(ProjectFrame.R().getString("Overview"), new JPanel());
        previewPane.addTab(ProjectFrame.R().getString("Sensitivity"), new JPanel());
        previewPane.setUI(new CustomTabbedPaneUI());
        previewPane.setTabPlacement(JTabbedPane.BOTTOM);

        TrialTable table=new TrialTable();
        JScrollPane JScrollPane1 = new JScrollPane(table);
        JScrollPane1.setViewportView(table);

        TitledPanel trials=new TitledPanel(R().getString("Trials"),JScrollPane1);
        TitledPanel properties=new TitledPanel(R().getString("Properties"),tabbedPane);
        TitledPanel preview=new TitledPanel(R().getString("Preview"),previewPane);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));
        panel.add(properties);
        panel.add(preview);
        add(panel);
        add(trials);
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
                JCommandMenuButton buttonNewText =new JCommandMenuButton(R().getString("NewTextTrial"),getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
                JCommandMenuButton buttonNewImage =new JCommandMenuButton(R().getString("NewImageTrial"),getResizableIconFromResource("oxygen/32x32/actions/list-add.png"));
                menu.addMenuButton(buttonNewSourceCode);
                menu.addMenuButton(buttonNewText);
                menu.addMenuButton(buttonNewImage);
                return menu;
            }
        });

        JCommandButton buttonUpdateTrial = new JCommandButton(R().getString("UpdateTrial"), getResizableIconFromResource("oxygen/32x32/actions/configure.png"));
        JCommandButton buttonRemoveTrial = new JCommandButton(R().getString("RemoveTrial"), getResizableIconFromResource("oxygen/32x32/actions/list-remove.png"));


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
