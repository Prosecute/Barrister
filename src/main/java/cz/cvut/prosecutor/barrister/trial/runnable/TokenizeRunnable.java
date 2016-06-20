package cz.cvut.prosecutor.barrister.trial.runnable;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.submissions.Submission;

public class TokenizeRunnable implements Runnable{

    private final Submission[] submissions;

    public TokenizeRunnable(Submission[] submissions)
    {
        this.submissions=submissions;
    }

    @Override
    public void run() {

    }
}
