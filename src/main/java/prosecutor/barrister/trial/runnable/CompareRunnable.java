package prosecutor.barrister.trial.runnable;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.Submission;

import java.util.Set;

public class CompareRunnable implements Runnable {

    private final Set<Submission> compared,tested;

    public CompareRunnable(Set<Submission> compared,Set<Submission> tested)
    {
        this.tested=tested;
        this.compared=compared;
    }
    @Override
    public void run() {

        for(Submission test:tested)
            for(Submission compare:compared)
                if(test!=compare)

    }
}
