package prosecutor.barrister.trial.runnable;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.languages.java_default.JavaParser;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.submissions.tokens.TokensMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TokenizeRunnable implements Runnable{

    private final Set<Submission> submissions;

    public TokenizeRunnable(Set<Submission> submissions)
    {
        this.submissions=submissions;
    }

    @Override
    public void run() {
        for(Submission sub:submissions)
        {
            JavaParser parser=new JavaParser();

            try {
                TokensMapping tokens=parser.parseTokens(sub.getFiles());
                sub.setSubmissionTokens(tokens);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
