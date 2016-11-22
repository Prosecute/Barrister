package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.common.*;
import org.pushingpixels.flamingo.api.common.model.ActionToggleButtonModel;
import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.components.LocationTable;
import prosecutor.barrister.gui.listener.MouseClickListener;
import prosecutor.barrister.gui.tabbedPane.CustomTabbedPaneUI;
import prosecutor.barrister.jaxb.EntitiesLocation;
import prosecutor.barrister.jaxb.TestMode;

import static prosecutor.barrister.gui.ProjectFrame.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

public class TestedFilePanel extends JPanel {

    public RibbonContextualTaskGroup group;
    private LocationTable table;
    public TestedFilePanel() {
        setLayout(new GridLayout(1, 2, 10, 10));


        JTree tree = new JTree(addNodes(null, new File("D://")));

        TitledPanel subs=new TitledPanel(R().getString("SelectedSubmissions"),new JScrollPane(tree));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(ProjectFrame.R().getString("Info"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("Filters"), new JPanel());
        tabbedPane.setUI(new CustomTabbedPaneUI());
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        TitledPanel properties=new TitledPanel(R().getString("Properties"),tabbedPane);


        table = new LocationTable(this);
        JScrollPane JScrollPane1 = new JScrollPane(table);
        JScrollPane1.setViewportView(table);
        TitledPanel tables=new TitledPanel(R().getString("Locations"),JScrollPane1);

        add(tables);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(subs);
        panel.add(properties);
        add(panel);

        setupRibbon();
    }
    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector ol = new Vector();
        String[] tmp = dir.list();
        for (int i = 0; i < tmp.length; i++)
            ol.addElement(tmp[i]);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector files = new Vector();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = (String) ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
            {}
            else
                files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }
    JRibbonBand bandNew,bandActions,bandCleanUp,bandProperties;
    JCommandButton buttonAddLocation,buttonDuplicate,buttonMoveUp,buttonMoveDown,buttonMoveTop,buttonMoveBottom,buttonRemoveLocation,
            buttonClearLocations,buttonFolderSync;
    JCommandToggleButton buttonDirect,buttonMode;
    public void setupRibbon()
    {

        bandNew = new JRibbonBand(R().getString("New"), null);
        bandActions = new JRibbonBand(R().getString("Actions"), null);
        bandCleanUp = new JRibbonBand(R().getString("CleanUp"), null);
        bandProperties = new JRibbonBand(R().getString("Properties"), null);
        buttonAddLocation = new JCommandButton(R().getString("AddLocation"), getResizableIconFromResource("oxygen/32x32/actions/folder-new.png"));
        buttonDuplicate = new JCommandButton(R().getString("DuplicateLocation"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        buttonMoveUp = new JCommandButton(R().getString("MoveUp"), getResizableIconFromResource("oxygen/32x32/actions/go-up.png"));
        buttonMoveDown = new JCommandButton(R().getString("MoveDown"), getResizableIconFromResource("oxygen/32x32/actions/go-down.png"));
        buttonMoveTop = new JCommandButton(R().getString("MoveTop"), getResizableIconFromResource("oxygen/32x32/actions/go-top.png"));
        buttonMoveBottom = new JCommandButton(R().getString("MoveBottom"), getResizableIconFromResource("oxygen/32x32/actions/go-bottom.png"));
        buttonRemoveLocation = new JCommandButton(R().getString("DeleteLocation"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        buttonClearLocations = new JCommandButton(R().getString("DeleteAllLocations"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        buttonFolderSync = new JCommandButton(R().getString("SyncLocation"), getResizableIconFromResource("oxygen/32x32/actions/folder-sync.png"));
        buttonDirect = new JCommandToggleButton(R().getString("Direct"), getResizableIconFromResource("oxygen/32x32/places/document-multiple.png"));
        buttonMode = new JCommandToggleButton(R().getString("CompareOnly"), getResizableIconFromResource("oxygen/32x32/actions/zoom-next.png"));


        buttonAddLocation.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().add(new EntitiesLocation());
                table.addNewLine();
                setActive(ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().size());

            }
        });
        buttonDuplicate.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow()<0)
                    return;
                ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().get(table.getSelectedRow());

            }
        });
        buttonRemoveLocation.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() < 0)
                    return;
                ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().remove(table.getSelectedRow());
                int i = table.getSelectedRow();
                table.removeLine(i);
                if (i > 0) i--;
                else if (table.getRowCount() > i)
                {}
                else
                    return;
                table.setRowSelectionInterval(i,i);
            }
        });

        bandNew.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandNew.getControlPanel())));
        bandActions.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandActions.getControlPanel())));
        bandCleanUp.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandCleanUp.getControlPanel())));
        bandProperties.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(bandProperties.getControlPanel())));

        bandNew.addCommandButton(buttonAddLocation, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonDuplicate,RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonRemoveLocation,RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveTop, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveUp, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveDown, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveBottom, RibbonElementPriority.TOP);
        bandCleanUp.addCommandButton(buttonFolderSync,RibbonElementPriority.MEDIUM);
        bandCleanUp.addCommandButton(buttonClearLocations,RibbonElementPriority.MEDIUM);
        bandProperties.addCommandButton(buttonDirect,RibbonElementPriority.TOP);
        bandProperties.addCommandButton(buttonMode,RibbonElementPriority.TOP);

        RibbonTask taskTestedFiles = new RibbonTask(ProjectFrame.R().getString("Files"), bandNew,bandActions,bandCleanUp,bandProperties);
        group = new RibbonContextualTaskGroup(ProjectFrame.R().getString("TestedFiles"), Color.yellow,
                taskTestedFiles );
        refreshRibbonState();
    }

    public void refreshRibbonState()
    {
        int s=ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().size();
        boolean b1=false,b2=true;
        if(s>0)
            b1=true;
        buttonDuplicate.setEnabled(b1);
        buttonClearLocations.setEnabled(b1);
        buttonFolderSync.setEnabled(b1);
        if(table.getSelectedRow()<0)
            b2=false;
        else
        {
            EntitiesLocation l=ProjectFrame.Configuration().getEntitiesLocations().getEntitiesLocation().get(table.getSelectedRow());
            ((ActionToggleButtonModel) buttonDirect.getActionModel()).setPressed(l.isDirect());
            ((ActionToggleButtonModel)buttonMode.getActionModel()).setPressed(l.getMode()==TestMode.COMPARE);
        }
        buttonDuplicate.setEnabled(b2);
        buttonMoveUp.setEnabled(b2);
        buttonMoveDown.setEnabled(b2);
        buttonMoveTop.setEnabled(b2);
        buttonMoveBottom.setEnabled(b2);
        buttonRemoveLocation.setEnabled(b2);
        buttonDirect.setEnabled(b2);
        buttonMode.setEnabled(b2);

    }

    public void refreshTable()
    {

    }
    public void setActive(int i)
    {

    }
}
