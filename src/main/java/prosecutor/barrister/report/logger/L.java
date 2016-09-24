package prosecutor.barrister.report.logger;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import prosecutor.barrister.jaxb.ConsoleLine;
import prosecutor.barrister.jaxb.Match;
import prosecutor.barrister.jaxb.Report;
import prosecutor.barrister.trial.Trial;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class L {


    private static Report report;

    public static void setReport(Report report)
    {
        L.report=report;
    }

    public static void logException(String source,String message,Exception exception)
    {
        StackTraceElement[] stackTraceElements=exception.getStackTrace();

        exception.printStackTrace();
    }

    public static void log(LoggerLevel level,String source,String message)
    {
        //TODO connect consoleline output
        ConsoleLine line=new ConsoleLine();
        line.setLevel(level.name().toUpperCase());
        line.setSource(source);

        //FIXME
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date(System.currentTimeMillis()/1000L));
        try {
            line.setTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        line.setValue(message);
    }
    public static MatchLogger createMatchLogger(Trial trial,String source)
    {
        return new MatchLogger(source,trial);
    }

    protected static Match findOrCreateMatch(int EL_A_ID, int EL_B_ID,String URL_A, String URL_B)
    {
        for(Match match:report.getMatches().getMatch())
        {
            if(match.getRefEntitiesLocationAID()==EL_A_ID && match.getRefEntitiesLocationBID()==EL_B_ID &&
                    match.getRefSubmissionA().equals(URL_A) && match.getRefSubmissionB().equals(URL_B))
                return match;
        }
        Match match=new Match();
        match.setRefEntitiesLocationAID(EL_A_ID);
        match.setRefEntitiesLocationBID(EL_B_ID);
        match.setRefSubmissionA(URL_A);
        match.setRefSubmissionB(URL_B);
        match.setTrialMatches(new Match.TrialMatches());
        report.getMatches().getMatch().add(match);
        return match;
    }

    public static void saveReport(Path path)
    {
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(Report.class);
            javax.xml.bind.Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(report,path.toFile());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
