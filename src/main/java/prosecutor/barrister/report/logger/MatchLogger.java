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
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.trial.Trial;
import prosecutor.barrister.trial.tiling.GSTilingExtended;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MatchLogger {

    private String source;

    private Trial trial;

    private Match.TrialMatches.TrialMatch.ConsoleOutput console=new Match.TrialMatches.TrialMatch.ConsoleOutput();

    protected MatchLogger(String source)
    {
        this.source="["+source+"]";
    }

    public void logMatch(GSTilingExtended.Match match,Submission subA,Submission subB)
    {
        Match output=new Match();
        output.setRefSubmissionA(subA.getLocation().toString());
        output.setRefSubmissionB(subB.getLocation().toString());
        output.setRefEntitiesLocationAID(subA.getSubmissionLocationID());
        output.setRefEntitiesLocationBID(subB.getSubmissionLocationID());
        Match.TrialMatches matches=new Match.TrialMatches();
        Match.TrialMatches.TrialMatch trialMatch=new Match.TrialMatches.TrialMatch();
        trialMatch.setTrialID(trial.getTrialID());
        trialMatch.setConsoleOutput(console);
        console=null;
        Match.TrialMatches.TrialMatch.TokenMatches tokenMatches=new Match.TrialMatches.TrialMatch.TokenMatches();
        trialMatch.setTokenMatches();
    }

    public void log(LoggerLevel level,String msg)
    {
        ConsoleLine line=new ConsoleLine();
        line.setSource(source);
        line.setLevel(level.name().toUpperCase());
        line.setSource(source);
        line.setTime(System.currentTimeMillis() / 1000L);
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
