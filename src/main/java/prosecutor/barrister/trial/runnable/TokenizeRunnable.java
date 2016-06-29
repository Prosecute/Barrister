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

public class TokenizeRunnable implements Runnable{

    private final Set<Submission> submissions;

    public TokenizeRunnable(Set<Submission> submissions)
    {
        this.submissions=submissions;
    }

    @Override
    public void run() {

    }
}
