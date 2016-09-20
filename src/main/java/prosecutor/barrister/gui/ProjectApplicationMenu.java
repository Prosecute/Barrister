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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);
        this.addMenuSeparator();
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-open.png"),ProjectFrame.R().getString("Load"), e -> {

        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-open-recent.png"),ProjectFrame.R().getString("LoadRecent"), e -> {

        }, JCommandButton.CommandButtonKind.POPUP_ONLY);
        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save.png"),ProjectFrame.R().getString("Save"), e -> {

        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        this.addMenuEntry(primary);

        primary =new RibbonApplicationMenuEntryPrimary(ProjectFrame.getResizableIconFromResource("oxygen/32x32/actions/document-save-as.png"),ProjectFrame.R().getString("SaveAs"), e -> {

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
