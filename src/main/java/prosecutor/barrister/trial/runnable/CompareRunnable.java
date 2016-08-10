package prosecutor.barrister.trial.runnable;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.report.logger.MatchLogger;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.trial.Trial;
import prosecutor.barrister.trial.tiling.GSTilingExtended;

import java.util.Set;

public class CompareRunnable implements Runnable {

    private final Set<Submission> compared,tested;

    private final Trial trial;

    public CompareRunnable(Set<Submission> compared,Set<Submission> tested,Trial trial)
    {
        this.trial=trial;
        this.tested=tested;
        this.compared=compared;
    }

    public void run() {

        for(Submission test:tested)
            for(Submission compare:compared)
                if(!test.getLocation().equals(compare.getLocation()))
                {
                    MatchLogger matchLogger= L.createMatchLogger(trial,"GSTilingExtended");
                    GSTilingExtended gs=new GSTilingExtended(matchLogger);
                    gs.compare(test,compare,5);
                }

    }
}
