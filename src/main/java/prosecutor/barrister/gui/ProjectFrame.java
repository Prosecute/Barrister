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
import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;
import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;
import org.pushingpixels.substance.internal.utils.SubstanceColorResource;
import prosecutor.barrister.gui.panels.BottomPanel;
import prosecutor.barrister.gui.panels.NoReportPanel;
import prosecutor.barrister.gui.panels.ProjectOverviewPanel;
import prosecutor.barrister.gui.panels.TestedFilePanel;
import prosecutor.barrister.jaxb.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProjectFrame extends JRibbonFrame {

    public static Locale CurrentLocale= Locale.ENGLISH;
    public static Configuration Configuration;

    public ProjectFrame()
    {

        this.setTitle(R().getString("Barrister"));
        try {
            this.setIconImage(ImageIO.read(ProjectFrame.class.getClassLoader().getResource("prosecutor/barrister/prosecutor2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }



        JRibbonBand band2 = new JRibbonBand(R().getString("Report"), null);
        JCommandButton button1 = new JCommandButton(R().getString("GenerateReport"), getResizableIconFromResource("oxygen/32x32/actions/debug-run.png"));
        band2.addCommandButton(button1,RibbonElementPriority.TOP);
        band2.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.None(band2.getControlPanel()),
                new IconRibbonBandResizePolicy(band2.getControlPanel())));
        RibbonTask task2 = new RibbonTask(R().getString("Task"), band2);
        this.getRibbon().addTask(task2);
        this.getRibbon().setApplicationMenu(new ProjectApplicationMenu(this));
        this.getRibbon().setApplicationIcon(getResizableIconFromResource("prosecutor.png"));

        this.setMinimumSize(new Dimension(750,450));


        BottomPanel bottomPanel =new BottomPanel();
        bottomPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));


        JTabbedPane tabbedPane = new JTabbedPane();
        String titles[] = {R().getString("ProjectOverview"), R().getString("TestedFiles"), R().getString("Trials"),  R().getString("Report") };

        int mnemonic[] = { KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4 };


        TestedFilePanel testedFilePanel=new TestedFilePanel();
        this.getRibbon().addContextualTaskGroup(testedFilePanel.groupTestedFiles);
        tabbedPane.addTab(titles[0],new ProjectOverviewPanel());
        tabbedPane.setMnemonicAt(0, mnemonic[0]);
        tabbedPane.addTab(titles[1],new TestedFilePanel());
        tabbedPane.setMnemonicAt(1, mnemonic[1]);
        tabbedPane.addTab(titles[2],new JPanel());
        tabbedPane.setMnemonicAt(2, mnemonic[2]);
        tabbedPane.addTab(titles[3],new NoReportPanel());
        tabbedPane.setMnemonicAt(3, mnemonic[3]);

        tabbedPane.addChangeListener(e -> {
            this.getRibbon().setVisible(testedFilePanel.groupTestedFiles,false);
            switch (((JTabbedPane)e.getSource()).getSelectedIndex())
            {
                case 1:
                    this.getRibbon().setVisible(testedFilePanel.groupTestedFiles,true);
                    this.getRibbon().setSelectedTask(testedFilePanel.groupTestedFiles.getTask(0));
                    break;
            }
        });


        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        this.add(tabbedPane,BorderLayout.CENTER);
        this.addComponentListener(new WindowSnapper());

    }

    public static ResourceBundle R()
    {
        return ResourceBundle.getBundle("prosecutor/barrister/lang/LangUI", CurrentLocale);
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(ProjectFrame.class.getClassLoader().getResource("prosecutor/barrister/"+resource), new Dimension(48, 48));
    }

    public static void main(String... args)
    {

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {


            @Override
            public void run() {

                try {
                    UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
                } catch (Exception e) {
                    System.out.println("Substance Graphite failed to initialize");
                }
                ProjectFrame frame = new ProjectFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
