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
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntrySecondary;
import prosecutor.barrister.gui.forms.TemplatesDialog;
import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.ConfigurationType;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.validation.SchemaFactory;
import java.awt.event.WindowEvent;
import java.io.File;

public class ProjectApplicationMenu extends RibbonApplicationMenu {

    public ProjectApplicationMenu(JFrame parrent)
    {


        RibbonApplicationMenuEntryFooter footer=new RibbonApplicationMenuEntryFooter(ProjectFrame.getResizableIconFromResource("oxygen/32x32/categories/applications-system.png"),ProjectFrame.R().getString("Options"),e-> {

        });
        this.addFooterEntry(footer);
        footer=new RibbonApplicationMenuEntryFooter(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/application-exit.png"),ProjectFrame.R().getString("CloseApplication"),e-> {
            parrent.dispatchEvent(new WindowEvent(parrent, WindowEvent.WINDOW_CLOSING));
        });
        this.addFooterEntry(footer);


        RibbonApplicationMenuEntryPrimary primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-new.png"),ProjectFrame.R().getString("New"), e -> {
           }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        RibbonApplicationMenuEntrySecondary secondary=new RibbonApplicationMenuEntrySecondary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-new.png"),
                ProjectFrame.R().getString("New"),e1 -> {
            ProjectFrame.setConfiguration(new Configuration());

        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        primary.addSecondaryMenuGroup(ProjectFrame.R().getString("New"),secondary);
        secondary =new RibbonApplicationMenuEntrySecondary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-new.png"),
                ProjectFrame.R().getString("FromTemplate"),e1 -> {
            final JDialog dialog=new TemplatesDialog(parrent,ProjectFrame.R().getString("Templates"),true);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        primary.addSecondaryMenuGroup(ProjectFrame.R().getString("Templates"),secondary);

        this.addMenuEntry(primary);
        this.addMenuSeparator();
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-open.png"),ProjectFrame.R().getString("Load"), e -> {
            JFileChooser c = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Barrister file (*.brr)", new String[] {"brr"});
            c.setFileFilter(filter);
            c.setCurrentDirectory(new File(AppConf.Instance().getOrDefault("cache","lastDirOpen","/")));
            int ret = c.showOpenDialog(parrent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                try {
                    JAXBContext jc = JAXBContext.newInstance(Configuration.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    if(!c.getSelectedFile().getName().endsWith(".brr"))
                        c.setSelectedFile(new File(c.getSelectedFile().getParent(),c.getName()+".brr"));
                    ProjectFrame.setConfiguration((Configuration) unmarshaller.unmarshal(c.getSelectedFile()));
                }
                catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
            AppConf.Instance().set("cache","lastDirOpen",c.getCurrentDirectory().toString());
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-open-recent.png"),ProjectFrame.R().getString("LoadRecent"), e -> {


        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save.png"),ProjectFrame.R().getString("Save"), e -> {
            if(ProjectFrame.ConfigurationFile!=null)
            {
                try {
                    JAXBContext jc = JAXBContext.newInstance(Configuration.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.marshal(ProjectFrame.Configuration(),ProjectFrame.ConfigurationFile);
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                JFileChooser c = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Barrister file (*.brr)", new String[]{"brr"});
                c.setCurrentDirectory(new File(AppConf.Instance().getOrDefault("cache", "lastDirSave", "/")));
                AppConf.Instance().set("cache", "lastDirSave", c.getCurrentDirectory().toString());
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save-as.png"),ProjectFrame.R().getString("SaveAs"), e -> {
            JFileChooser c = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Barrister file (*.brr)", new String[] {"brr"});
            c.setFileFilter(filter);
            c.setCurrentDirectory(new File(AppConf.Instance().getOrDefault("cache","lastDirSave","/")));
            int ret=c.showSaveDialog(parrent);
            if(ret==JFileChooser.APPROVE_OPTION)
            {
                if(!c.getSelectedFile().getAbsolutePath().endsWith(".brr"))
                    c.setSelectedFile(new File(c.getSelectedFile().getAbsolutePath()+".brr"));
                ProjectFrame.ConfigurationFile=c.getSelectedFile();
                try {
                    JAXBContext jc = JAXBContext.newInstance(Configuration.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.marshal(ProjectFrame.Configuration(),ProjectFrame.ConfigurationFile);
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
            AppConf.Instance().set("cache","lastDirSave",c.getCurrentDirectory().toString());
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);
        this.addMenuSeparator();
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-preview.png"),ProjectFrame.R().getString("Info"), e -> {

        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        this.addMenuEntry(primary);
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-import.png"),ProjectFrame.R().getString("Import"), e -> {

        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        primary.setEnabled(false);
        this.addMenuEntry(primary);
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-export.png"),ProjectFrame.R().getString("Export"), e -> {

        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        primary.setEnabled(false);
        this.addMenuEntry(primary);
    }
}
