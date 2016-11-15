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
import prosecutor.barrister.utils.TimeUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReportConsolePanel extends JPanel {



    public ReportConsolePanel()
    {
        StyleContext sc = new StyleContext();
        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane =new JTextPane(doc);
        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
        StyleConstants.setFontFamily(mainStyle, "Monospaced");
        final Style warnStyle = sc.addStyle("WarnStyle", mainStyle);
        final Style infoStyle = sc.addStyle("InfoStyle", mainStyle);
        final Style debugStyle = sc.addStyle("DebugStyle", mainStyle);
        final Style fatalStyle = sc.addStyle("FatalStyle", mainStyle);
        final Style errorStyle = sc.addStyle("ErrorStyle", mainStyle);
        StyleConstants.setForeground(mainStyle,Color.BLACK);
        StyleConstants.setForeground(infoStyle,Color.GRAY);
        StyleConstants.setForeground(warnStyle,Color.YELLOW);
        StyleConstants.setForeground(debugStyle,Color.DARK_GRAY);
        StyleConstants.setForeground(fatalStyle,Color.ORANGE);
        StyleConstants.setForeground(errorStyle,Color.RED);

        DateFormat dt = new SimpleDateFormat("dd/mm/yy hh:mm");
        for(ConsoleLine line: ProjectFrame.Report().getConsoleOutput().getConsoleLine())
        {
            try {
                Style style;
                switch (line.getLevel().toLowerCase())
                {
                    case "debug": style=debugStyle; break;
                    case "error": style=errorStyle; break;
                    case "warn": style=warnStyle; break;
                    case "fatal": style=fatalStyle; break;
                    default:
                        style=infoStyle; break;
                }
                doc.insertString(doc.getLength(),"["+line.getLevel()+"/", mainStyle);
                doc.insertString(doc.getLength(),line.getSource()+"]",mainStyle);
                doc.insertString(doc.getLength(), dt.format(TimeUtils.toDate(line.getTime()))+": ",mainStyle);
                doc.insertString(doc.getLength(),line.getValue(),style);
                doc.insertString(doc.getLength(),System.getProperty("line.separator"),mainStyle);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        setLayout(new BorderLayout());
        this.add(pane);
    }
}
