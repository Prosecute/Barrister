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
import prosecutor.barrister.gui.panels.*;
import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.Report;
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.tasks.CompareTask;

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
    private static Configuration Configuration;
    private static Report report;
    public static File ProjectFolder=null;
    public static File ConfigurationFile=null;
    public static Configuration Configuration()
    {
        if(Configuration==null) {
            Configuration = new Configuration();
            Configuration.setEntitiesLocations(new Configuration.EntitiesLocations());
            Configuration.setTrials(new Configuration.Trials());
        }
        return Configuration;
    }
    public static Report Report()
    {
        return report;
    }
    public static void setConfiguration(Configuration conf)
    {
        ProjectFrame.Configuration = conf;
    }
    private static ProjectFrame Instance;
    public static ProjectFrame Instance()
    {
        if(Instance==null)
            Instance=new ProjectFrame();
        return Instance;
    }

    public ProjectFrame()
    {

        this.setTitle(R().getString("Barrister"));
        this.setIconImage(getImageIO("prosecutor2.png"));



        JRibbonBand band2 = new JRibbonBand(R().getString("Report"), null);
        JCommandButton button1 = new JCommandButton(R().getString("Generate"), getResizableIconFromResource("oxygen/32x32/actions/run-build.png"));
        button1.addActionListener(l -> {
            CompareTask task=new CompareTask();
            task.setConfiguration(Configuration);
            report=task.generateReport();
            report=report;
        });
        JCommandButton button2 = new JCommandButton(R().getString("CleanGenerate"), getResizableIconFromResource("oxygen/32x32/actions/run-build-clean.png"));
        JCommandButton button3 = new JCommandButton(R().getString("Debug"), getResizableIconFromResource("oxygen/32x32/actions/debug-run.png"));
        button3.addActionListener(l -> {
            L.debug=true;
            CompareTask task=new CompareTask();
            task.setConfiguration(Configuration);
            report=task.generateReport();
            report=report;
        });
        JCommandButton button4 = new JCommandButton(R().getString("GenerateToFile"), getResizableIconFromResource("oxygen/32x32/actions/run-build-file.png"));
        JCommandButton button5 = new JCommandButton(R().getString("GenerateConfigure"), getResizableIconFromResource("oxygen/32x32/actions/run-build-configure.png"));
        band2.addCommandButton(button1,RibbonElementPriority.TOP);
        band2.addCommandButton(button2,RibbonElementPriority.TOP);
        band2.addCommandButton(button3,RibbonElementPriority.MEDIUM);
        band2.addCommandButton(button4,RibbonElementPriority.MEDIUM);
        band2.addCommandButton(button5,RibbonElementPriority.MEDIUM);
        band2.setResizePolicies((List) Arrays.asList(
                new CoreRibbonResizePolicies.Mirror(band2.getControlPanel()),
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
        ReportPanel reportPanel=new ReportPanel(new Report());
        TrialsPanel trialsPanel=new TrialsPanel(Configuration.getTrials());
        this.getRibbon().addContextualTaskGroup(testedFilePanel.group);
        this.getRibbon().addContextualTaskGroup(reportPanel.group);
        this.getRibbon().addContextualTaskGroup(trialsPanel.group);
        tabbedPane.addTab(titles[0],new ProjectOverviewPanel());
        tabbedPane.setMnemonicAt(0, mnemonic[0]);
        tabbedPane.addTab(titles[1],testedFilePanel);
        tabbedPane.setMnemonicAt(1, mnemonic[1]);
        tabbedPane.addTab(titles[2],trialsPanel);
        tabbedPane.setMnemonicAt(2, mnemonic[2]);
        tabbedPane.addTab(titles[3],reportPanel);
        //tabbedPane.addTab(titles[3],new NoReportPanel());
        tabbedPane.setMnemonicAt(3, mnemonic[3]);

        tabbedPane.addChangeListener(e -> {
            this.getRibbon().setVisible(testedFilePanel.group,false);
            this.getRibbon().setVisible(reportPanel.group,false);
            this.getRibbon().setVisible(trialsPanel.group,false);
            switch (((JTabbedPane)e.getSource()).getSelectedIndex())
            {
                case 1:
                    this.getRibbon().setVisible(testedFilePanel.group,true);
                    this.getRibbon().setSelectedTask(testedFilePanel.group.getTask(0));
                    break;
                case 2:
                    this.getRibbon().setVisible(trialsPanel.group,true);
                    this.getRibbon().setSelectedTask(trialsPanel.group.getTask(0));
                    break;
                case 3:
                    this.getRibbon().setVisible(reportPanel.group,true);
                    this.getRibbon().setSelectedTask(reportPanel.group.getTask(0));
                    break;
            }
        });


        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        this.add(tabbedPane,BorderLayout.CENTER);

    }

    public static Image getImageIO(String src)
    {
        try {
            return ImageIO.read(ProjectFrame.class.getClassLoader().getResource("prosecutor/barrister/" + src));
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public static Icon getIconIO(String src)
    {
        return new ImageIcon(getImageIO(src));
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
                ProjectFrame frame = ProjectFrame.Instance();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
