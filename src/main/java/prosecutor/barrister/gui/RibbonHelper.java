package prosecutor.barrister.gui;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.common.model.PopupButtonModel;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.tasks.CompareTask;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RibbonHelper {


    public static void createTaskTab(ProjectFrame frame)
    {
        JRibbonBand band2 = new JRibbonBand(R().getString("Report"), null);
        JCommandButton button1 = new JCommandButton(R().getString("Generate"), getResizableIconFromResource("oxygen/32x32/actions/run-build.png"));
        button1.addActionListener(l -> {
            frame.generateReport();
        });
        JCommandButton button2 = new JCommandButton(R().getString("CleanGenerate"), getResizableIconFromResource("oxygen/32x32/actions/run-build-clean.png"));
        JCommandButton button3 = new JCommandButton(R().getString("Debug"), getResizableIconFromResource("oxygen/32x32/actions/debug-run.png"));
        button3.addActionListener(l -> {
            L.debug=true;
            frame.generateReport();
        });
        JCommandButton button4 = new JCommandButton(R().getString("GenerateToFile"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        JCommandButton button5 = new JCommandButton(R().getString("GenerateConfigure"), getResizableIconFromResource("oxygen/32x32/actions/run-build-configure.png"));
        band2.addCommandButton(button1, RibbonElementPriority.TOP);
        band2.addCommandButton(button2,RibbonElementPriority.TOP);
        band2.addCommandButton(button3,RibbonElementPriority.MEDIUM);
        band2.addCommandButton(button4,RibbonElementPriority.MEDIUM);
        band2.addCommandButton(button5,RibbonElementPriority.MEDIUM);
        band2.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band2.getControlPanel()),
                new IconRibbonBandResizePolicy(band2.getControlPanel())));
        RibbonTask task2 = new RibbonTask(R().getString("Tasks"), band2);
        frame.getRibbon().addTask(task2);
    }

    public static void createHomeTab(ProjectFrame frame)
    {
        JRibbonBand band = new JRibbonBand(R().getString("Views"), null);
        JCommandButton button = new JCommandButton(R().getString("View"), getResizableIconFromResource("oxygen/32x32/actions/view-file-columns.png"));
        band.addCommandButton(button,RibbonElementPriority.TOP);
        band.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band.getControlPanel()),
                new IconRibbonBandResizePolicy(band.getControlPanel())));
        JRibbonBand band2 = new JRibbonBand(R().getString("Clipboard"), null);
        button = new JCommandButton(R().getString("Paste"), getResizableIconFromResource("oxygen/32x32/actions/edit-paste.png"));
        band2.addCommandButton(button,RibbonElementPriority.TOP);
        button = new JCommandButton(R().getString("Cut"), getResizableIconFromResource("oxygen/32x32/actions/edit-cut.png"));
        band2.addCommandButton(button,RibbonElementPriority.MEDIUM);
        button = new JCommandButton(R().getString("Copy"), getResizableIconFromResource("oxygen/32x32/actions/edit-copy.png"));
        band2.addCommandButton(button,RibbonElementPriority.MEDIUM);
        band2.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band2.getControlPanel()),
                new IconRibbonBandResizePolicy(band2.getControlPanel())));
        JRibbonBand band3= new JRibbonBand(R().getString("Find"), null);
        button = new JCommandButton(R().getString("Find"), getResizableIconFromResource("oxygen/32x32/actions/edit-find.png"));
        band3.addCommandButton(button,RibbonElementPriority.TOP);
        button = new JCommandButton(R().getString("FindProject"), getResizableIconFromResource("oxygen/32x32/actions/edit-find-project.png"));
        band3.addCommandButton(button,RibbonElementPriority.MEDIUM);
        button = new JCommandButton(R().getString("Replace"), getResizableIconFromResource("oxygen/32x32/actions/edit-find-replace.png"));
        band3.addCommandButton(button,RibbonElementPriority.MEDIUM);
        button = new JCommandButton(R().getString("GoTo"), getResizableIconFromResource("oxygen/32x32/actions/edit-find-replace.png"));
        button.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        band3.addCommandButton(button,RibbonElementPriority.MEDIUM);

        band3.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band3.getControlPanel()),
                new IconRibbonBandResizePolicy(band3.getControlPanel())));
        RibbonTask task = new RibbonTask(R().getString("Home"), band,band2,band3);
        frame.getRibbon().addTask(task);
    }

    public static void createExternalDataTab(ProjectFrame frame)
    {
        final JRibbonBand band = new JRibbonBand(R().getString("ImportFromBrr"), null);
        JCommandButton button = new JCommandButton(R().getString("ImportSubmissions"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        JCommandButton button2 = new JCommandButton(R().getString("ImportTrials"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        JCommandButton button3 = new JCommandButton(R().getString("ImportProjectConf"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        band.addCommandButton(button,RibbonElementPriority.TOP);
        band.addCommandButton(button2,RibbonElementPriority.TOP);
        band.addCommandButton(button3,RibbonElementPriority.TOP);
        band.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band.getControlPanel()),
                new IconRibbonBandResizePolicy(band.getControlPanel())));
        final JRibbonBand band2 = new JRibbonBand(R().getString("Export"), null);
        button = new JCommandButton(R().getString("ExportXML"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        band2.addCommandButton(button,RibbonElementPriority.TOP);
        band2.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band2.getControlPanel()),
                new IconRibbonBandResizePolicy(band2.getControlPanel())));
        RibbonTask task = new RibbonTask(R().getString("ExternalData"), band,band2);
        frame.getRibbon().addTask(task);
    }


    private static ResizableIcon getResizableIconFromResource(String resource) {
        return ProjectFrame.getResizableIconFromResource(resource);
    }
    private static ResourceBundle R()
    {
        return ProjectFrame.R();
    }
}
