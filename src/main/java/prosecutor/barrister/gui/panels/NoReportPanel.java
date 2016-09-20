package prosecutor.barrister.gui.panels;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.gui.ProjectFrame;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class NoReportPanel extends JPanel {

    public NoReportPanel()
    {
        JEditorPane jEditorPane = new JEditorPane();

        // make it read-only
        jEditorPane.setEditable(false);


        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);

        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; background: transparent;}");

        // create some simple html as a string
        String htmlString = "<html>\n"
                + "<body>\n"
                + "<p>"+ ProjectFrame.R().getString("NoReportMessage")+"\n"
                + "<br/><a href=\"http://devdaily.com/blog/\">"+ ProjectFrame.R().getString("NoReportMessageLink")+"</a></p>\n"
                + "</body>\n"
                + "</html>\n";

        // create a document, set it on the jeditorpane, then add the html
        Document doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);
        jEditorPane.setText(htmlString);
        setLayout(new GridBagLayout());
        this.add(jEditorPane);
    }
}
