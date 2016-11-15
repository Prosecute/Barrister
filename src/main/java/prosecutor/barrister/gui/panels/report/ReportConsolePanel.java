package prosecutor.barrister.gui.panels.report;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.ProjectFrame;
import prosecutor.barrister.jaxb.ConsoleLine;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;
import java.awt.*;

public class ReportConsolePanel extends JPanel {



    public ReportConsolePanel()
    {
        StyleContext sc = new StyleContext();
        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane =new JTextPane(doc);
        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
        StyleConstants.setFontFamily(mainStyle, "Monospaced");

        for(ConsoleLine line: ProjectFrame.Report().getConsoleOutput().getConsoleLine())
        {
            try {
                doc.insertString(doc.getLength(),"["+line.getLevel()+"/", mainStyle);
                doc.insertString(doc.getLength(),line.getSource()+"]",mainStyle);
                doc.insertString(doc.getLength(),line.getTime().toString()+": ",mainStyle);
                doc.insertString(doc.getLength(),line.getValue(),mainStyle);
                doc.insertString(doc.getLength(),System.getProperty("line.separator"),mainStyle);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        setLayout(new BorderLayout());
        this.add(pane);
    }
}
