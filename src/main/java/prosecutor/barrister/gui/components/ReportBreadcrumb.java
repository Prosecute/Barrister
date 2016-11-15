package prosecutor.barrister.gui.components;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarCallBack;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarException;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbItem;
import org.pushingpixels.flamingo.api.common.StringValuePair;
import prosecutor.barrister.gui.panels.report.ReportConfigurationPanel;
import prosecutor.barrister.gui.panels.report.ReportConsolePanel;
import prosecutor.barrister.gui.panels.report.ReportOverviewPanel;
import prosecutor.barrister.gui.panels.report.ReportXMLPanel;
import prosecutor.barrister.jaxb.Report;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static prosecutor.barrister.gui.ProjectFrame.*;

public class ReportBreadcrumb extends BreadcrumbBarCallBack<ReportBreadcrumb.RBLeaf> {

    private Report report;
    public final RBLeafCore core;
    private JComponent Overview,Matches,Console,Configuration,Xml;

    public JComponent getOverview()
    {
        if(Overview==null)
            Overview=new ReportOverviewPanel();
        return Overview;
    }
    public JComponent getConsole()
    {
        if(Console==null)
            Console = new ReportConsolePanel();
        return Console;
    }
    public JComponent getConfiguration()
    {
        if(Configuration==null)
            Configuration = new ReportConfigurationPanel();
        return Configuration;
    }
    public JComponent getXmlView()
    {
        if(Xml==null)
            Xml= new ReportXMLPanel();
        return Xml;
    }

    public ReportBreadcrumb(Report report)
    {
        super();
        this.report=report;
        this.throwsExceptions=false;
        this.core=new RBLeafCore();

    }

    @Override
    public List<StringValuePair<ReportBreadcrumb.RBLeaf>> getPathChoices(List<BreadcrumbItem<ReportBreadcrumb.RBLeaf>> path)
            throws BreadcrumbBarException {
        if(path==null)
            return Arrays.asList(new StringValuePair<RBLeaf>(core.Name,(RBLeaf)core));
        if(path.get(path.size()-1).getData().childs==null)
            return null;
        List<StringValuePair<ReportBreadcrumb.RBLeaf>> output =new ArrayList<>();
        for(ReportBreadcrumb.RBLeaf leaf:path.get(path.size()-1).getData().childs)
            output.add(new StringValuePair<>(leaf.Name,leaf));
        return output;
    }

    @Override
    public List<StringValuePair<ReportBreadcrumb.RBLeaf>> getLeafs(List<BreadcrumbItem<ReportBreadcrumb.RBLeaf>> path)
            throws BreadcrumbBarException {
        if(path==null)
            return Arrays.asList(new StringValuePair<RBLeaf>(core.Name,(RBLeaf)core));
        if(path.get(path.size()-1).getData().childs==null)
            return null;
        List<StringValuePair<ReportBreadcrumb.RBLeaf>> output =new ArrayList<>();
        for(ReportBreadcrumb.RBLeaf leaf:path.get(path.size()-1).getData().childs)
            output.add(new StringValuePair<>(leaf.Name,leaf));
        return output;
    }


    public abstract class RBLeaf
    {
        public String Name;
        public List<RBLeaf> childs;

        public abstract JComponent createView();
    }
    public class RBLeafCore extends RBLeaf
    {
        {
            Name=R().getString("Report");
            childs=new ArrayList<>();
            childs.add(new RBMatches());
            childs.add(new RBConf());
            childs.add(new RBConsole());
            childs.add(new RBError());
            childs.add(new RBXmlView());
        }

        @Override
        public JComponent createView() {
            return getOverview();
        }
    }
    public class RBMatches extends RBLeaf
    {
        {
            Name=R().getString("Matches");
        }
        @Override
        public JComponent createView() {
            return null;
        }
    }
    public class RBConsole extends RBLeaf
    {
        {
            Name=R().getString("Console");
        }
        @Override
        public JComponent createView() {
            return getConsole();
        }
    }
    public class RBConf extends RBLeaf
    {
        {
            Name=R().getString("Configuration");
        }
        @Override
        public JComponent createView() {
            return getConfiguration();
        }
    }
    public class RBError extends RBLeaf
    {
        {
            Name=R().getString("Errors");
        }
        @Override
        public JComponent createView() {
            return null;
        }
    }
    public class RBXmlView extends RBLeaf
    {
        {
            Name=R().getString("XmlView");
        }
        @Override
        public JComponent createView() {
            return getXmlView();
        }
    }
}
