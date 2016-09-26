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
import prosecutor.barrister.jaxb.ConfigurationType;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.awt.event.WindowEvent;

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
            ProjectFrame.setConfiguration(new ConfigurationType());

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
            int ret = c.showOpenDialog(parrent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                try {
                    JAXBContext jc = JAXBContext.newInstance(ConfigurationType.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    ProjectFrame.setConfiguration((ConfigurationType) unmarshaller.unmarshal(c.getSelectedFile()));
                }
                catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-open-recent.png"),ProjectFrame.R().getString("LoadRecent"), e -> {

        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save.png"),ProjectFrame.R().getString("Save"), e -> {

        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save-as.png"),ProjectFrame.R().getString("SaveAs"), e -> {
            JFileChooser c = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Barrister file (*.brr)", new String[] {"brr"});
            c.setFileFilter(filter);
            int ret=c.showSaveDialog(parrent);
            if(ret==JFileChooser.APPROVE_OPTION)
            {

            }
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
