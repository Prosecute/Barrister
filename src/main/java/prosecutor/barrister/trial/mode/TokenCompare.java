package prosecutor.barrister.trial.mode;
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

public class TokenCompare implements MultipleSubmissionMode {

    @Override
    public void run(Submission tested, Submission compared) {

        MatchLogger matchLogger= L.createMatchLogger(Trial.getCurrent(),"GSTilingExtended");
        GSTilingExtended gs=new GSTilingExtended(matchLogger);
        gs.compare(tested,compared,5);
    }
}
