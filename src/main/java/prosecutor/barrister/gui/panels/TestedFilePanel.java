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
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.gui.tabbedPane.CustomTabbedPaneUI;

import static prosecutor.barrister.gui.ProjectFrame.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class TestedFilePanel extends JPanel {

    public RibbonContextualTaskGroup groupTestedFiles;
    public TestedFilePanel() {
        setLayout(new GridLayout(1, 2, 10, 10));


        JTree tree = new JTree(addNodes(null, new File("D://Dokumenty/Projekt/Gitlab/Prosecutor/modules/Barrister/src/main/resources/prosecutor/barrister/oxygen/32x32/actions")));

        TitledPanel subs=new TitledPanel(R().getString("SelectedSubmissions"),new Color(51,51,221),new JScrollPane(tree));

        JTabbedPane tabbedPane = new JTabbedPane();
        String[] columnNames = {R().getString("TableFilesID"),
                R().getString("TableFilesName"),
                R().getString("TableFilesURL"),
                R().getString("TableFilesDirect"),
                R().getString("TableFilesMode")

        };
        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };
        tabbedPane.addTab(ProjectFrame.R().getString("Info"), new JPanel());
        tabbedPane.addTab(ProjectFrame.R().getString("Filters"), new JPanel());
        tabbedPane.setUI(new CustomTabbedPaneUI());
        TitledPanel properties=new TitledPanel(R().getString("Properties"),new Color(221,51 ,51),tabbedPane);
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMinWidth(50);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
        table.getColumnModel().getColumn(4).setMinWidth(50);
        table.getColumnModel().getColumn(1).setWidth(100);
        JScrollPane JScrollPane1 = new JScrollPane(table);
        JScrollPane1.setViewportView(table);
        table.setFillsViewportHeight(true);
        TitledPanel tables=new TitledPanel(R().getString("Locations"),new Color(51,221,51),JScrollPane1);

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
    public void setupRibbon()
    {

        JRibbonBand bandNew = new JRibbonBand(R().getString("New"), null);
        JRibbonBand bandActions = new JRibbonBand(R().getString("Actions"), null);
        JRibbonBand bandCleanUp = new JRibbonBand(R().getString("CleanUp"), null);
        JRibbonBand bandProperties = new JRibbonBand(R().getString("Properties"), null);
        JCommandButton buttonAddLocation = new JCommandButton(R().getString("AddLocation"), getResizableIconFromResource("oxygen/32x32/actions/folder-new.png"));
        JCommandButton buttonDuplicate = new JCommandButton(R().getString("DuplicateLocation"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        JCommandButton buttonMoveUp = new JCommandButton(R().getString("MoveUp"), getResizableIconFromResource("oxygen/32x32/actions/go-up.png"));
        JCommandButton buttonMoveDown = new JCommandButton(R().getString("MoveDown"), getResizableIconFromResource("oxygen/32x32/actions/go-down.png"));
        JCommandButton buttonMoveTop = new JCommandButton(R().getString("MoveTop"), getResizableIconFromResource("oxygen/32x32/actions/go-top.png"));
        JCommandButton buttonMoveBottom = new JCommandButton(R().getString("MoveBottom"), getResizableIconFromResource("oxygen/32x32/actions/go-bottom.png"));
        JCommandButton buttonRemoveLocation = new JCommandButton(R().getString("DeleteLocation"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        JCommandButton buttonClearLocations = new JCommandButton(R().getString("DeleteAllLocations"), getResizableIconFromResource("oxygen/32x32/places/folder.png"));
        JCommandButton buttonFolderSync = new JCommandButton(R().getString("SyncLocation"), getResizableIconFromResource("oxygen/32x32/actions/folder-sync.png"));
        JCommandToggleButton buttonDirect = new JCommandToggleButton(R().getString("Direct"), getResizableIconFromResource("oxygen/32x32/places/document-multiple.png"));
        JCommandToggleButton buttonMode = new JCommandToggleButton(R().getString("CompareOnly"), getResizableIconFromResource("oxygen/32x32/actions/zoom-next.png"));
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
        bandActions.addCommandButton(buttonMoveTop, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveUp, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveDown, RibbonElementPriority.TOP);
        bandActions.addCommandButton(buttonMoveBottom, RibbonElementPriority.TOP);
        bandCleanUp.addCommandButton(buttonRemoveLocation,RibbonElementPriority.TOP);
        bandCleanUp.addCommandButton(buttonFolderSync,RibbonElementPriority.MEDIUM);
        bandCleanUp.addCommandButton(buttonClearLocations,RibbonElementPriority.MEDIUM);
        bandProperties.addCommandButton(buttonDirect,RibbonElementPriority.TOP);
        bandProperties.addCommandButton(buttonMode,RibbonElementPriority.TOP);

        RibbonTask taskTestedFiles = new RibbonTask(ProjectFrame.R().getString("Files"), bandNew,bandActions,bandCleanUp,bandProperties);
        groupTestedFiles = new RibbonContextualTaskGroup(ProjectFrame.R().getString("TestedFiles"), SubstanceLookAndFeel.getCurrentSkin().getActiveColorScheme(DecorationAreaType.GENERAL).getSelectionForegroundColor(),
                taskTestedFiles );
    }

}
