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
import java.util.Stack;

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
        Match.TrialMatches.TrialMatch.TokenMatches tokenMatches=new Match.TrialMatches.TrialMatch.TokenMatches();
        List<Match.TrialMatches.TrialMatch.TokenMatches.RangeMatch> rangeMatch=tokenMatches.getRangeMatch();
        for(GSTilingExtended.Match match:matches)
            tokenMatches.getRangeMatch().add(match.toRangeMatch());
        trialMatch.setTokenMatches(tokenMatches);
        output.getTrialMatches().getTrialMatch().add(trialMatch);

        console=new Match.TrialMatches.TrialMatch.ConsoleOutput();
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
