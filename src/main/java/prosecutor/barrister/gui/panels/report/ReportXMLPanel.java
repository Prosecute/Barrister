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
import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.Report;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.StringWriter;

public class ReportXMLPanel extends JPanel {
    public ReportXMLPanel()
    {

        StyleContext sc = new StyleContext();
        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane =new JTextPane(doc);
        java.io.StringWriter sw = new StringWriter();

        try {
            JAXBContext jc = JAXBContext.newInstance(Report.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(ProjectFrame.Report(),sw);
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        try {
            doc.insertString(doc.getLength(),sw.toString(),defaultStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        this.add(pane);
    }
}
