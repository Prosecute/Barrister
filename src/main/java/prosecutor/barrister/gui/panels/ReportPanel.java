package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarModel;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbItem;
import org.pushingpixels.flamingo.api.bcb.JBreadcrumbBar;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonContextualTaskGroup;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import prosecutor.barrister.gui.components.ReportBreadcrumb;
import prosecutor.barrister.gui.panels.report.ReportOverviewPanel;
import prosecutor.barrister.jaxb.Report;

import static prosecutor.barrister.gui.ProjectFrame.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ReportPanel extends JPanel {

    public RibbonContextualTaskGroup group;

    private TitledPanel titledPanel;

    public ReportPanel(Report report)
    {
        setLayout(new GridLayout(1,1));

        ReportBreadcrumb breadcrumb=new ReportBreadcrumb(report);
        JBreadcrumbBar bar=new JBreadcrumbBar(breadcrumb);
        titledPanel=new TitledPanel("TODO",false,breadcrumb.getOverview(),bar);
        bar.getModel().addPathListener(event -> {
            List<BreadcrumbItem<ReportBreadcrumb.RBLeaf>> list=((BreadcrumbBarModel)event.getSource()).getItems();
            if(list==null)
                return;
            ReportBreadcrumb.RBLeaf leaf=list.get(list.size()-1).getData();
            titledPanel.changeMainComponent(leaf.createView());
        });
        bar.getModel().addLast(new BreadcrumbItem(breadcrumb.core.Name,breadcrumb.core));
        add(titledPanel);
        setupRibbon();
    }

    public void setupRibbon()
    {

        JRibbonBand band2 = new JRibbonBand(R().getString("Report"), null);
        JCommandButton button1 = new JCommandButton(R().getString("GenerateReport"), getResizableIconFromResource("oxygen/32x32/actions/debug-run.png"));
        band2.addCommandButton(button1, RibbonElementPriority.TOP);
        band2.setResizePolicies((java.util.List) Arrays.asList(
                new CoreRibbonResizePolicies.None(band2.getControlPanel()),
                new IconRibbonBandResizePolicy(band2.getControlPanel())));

        JRibbonBand band = new JRibbonBand(R().getString("Report"), null);
        JCommandButton button = new JCommandButton(R().getString("GenerateReport"), getResizableIconFromResource("oxygen/32x32/actions/debug-run.png"));
        band.addCommandButton(button, RibbonElementPriority.TOP);
        band.setResizePolicies((java.util.List) Arrays.asList(
                new CoreRibbonResizePolicies.None(band.getControlPanel()),
                new IconRibbonBandResizePolicy(band.getControlPanel())));

        RibbonTask bandExport=new RibbonTask(R().getString("Export"),band2);
        RibbonTask bandData=new RibbonTask(R().getString("Data"),band);


        group=new RibbonContextualTaskGroup(R().getString("Report"), Color.yellow,bandData,bandExport);
    }
}
