package prosecutor.barrister.report.logger;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.ConsoleLine;
import prosecutor.barrister.jaxb.Match;
import prosecutor.barrister.jaxb.TokenCompareMatch;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.submissions.tokens.Token;
import prosecutor.barrister.trial.Trial;
import prosecutor.barrister.trial.mode.TokenCompare;
import prosecutor.barrister.trial.tiling.GSTilingExtended;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class MatchLogger {

    private String source;

    private Trial trial;

    private Match.TrialMatches.TrialMatch.ConsoleOutput console=new Match.TrialMatches.TrialMatch.ConsoleOutput();

    protected MatchLogger(String source, Trial trial)
    {
        this.source="["+source+"]";
        this.trial=trial;
    }

    public void logMatch(Stack<GSTilingExtended.Match> matches,Submission subA,Submission subB)
    {
        Match output=L.findOrCreateMatch(subA.getSubmissionLocationID(),subB.getSubmissionLocationID(),subA.getLocation().toString(),subB.getLocation().toString());

        Match.TrialMatches.TrialMatch trialMatch=new Match.TrialMatches.TrialMatch();
        trialMatch.setTrialID(trial.getTrialID());
        trialMatch.setConsoleOutput(console);
        TokenCompareMatch tokenMatches=new TokenCompareMatch();
        for(GSTilingExtended.Match match:matches)
            tokenMatches.getRangeMatch().add(match.toRangeMatch());
        trialMatch.setResults(new Match.TrialMatches.TrialMatch.Results());
        trialMatch.getResults().setTokenCompare(tokenMatches);
        output.getTrialMatches().getTrialMatch().add(trialMatch);
        List<Token> list=subA.getSubmissionTokens().tokenList;
        boolean[] marked=new boolean[list.size()];
        for(GSTilingExtended.Match match:matches)
        {
            for(int i=match.PositionTokenA;i<match.PositionTokenA+match.Length;i++)
            {
                marked[i]=true;
            }
        }
        double mark=0;
        for(boolean b:marked)
            if(b)mark++;
        double res=(mark/list.size())*100;
        output.setMatch(new BigDecimal(res));
        console=new Match.TrialMatches.TrialMatch.ConsoleOutput();

    }

    public void log(LoggerLevel level,String msg)
    {
        ConsoleLine line=new ConsoleLine();
        line.setSource(source);
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
        line.setValue(msg);
        console.getConsoleLine().add(line);
    }
    public void logInfo(String msg)
    {
        log(LoggerLevel.INFO,msg);
    }
    public void logWarn(String msg)
    {
        log(LoggerLevel.WARN,msg);
    }
    public void logError(String msg)
    {
        log(LoggerLevel.ERROR,msg);
    }
    public void logFatal(String msg)
    {
        log(LoggerLevel.FATAL,msg);
    }
    public void logDebug(String msg)
    {
        log(LoggerLevel.DEBUG,msg);
    }


}
