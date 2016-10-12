package prosecutor.barrister.trial.mode.prepare;

import prosecutor.barrister.languages.java_default.JavaParser;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.submissions.tokens.TokensMapping;

import java.io.IOException;

/**
 * Created by Fry on 05.10.2016.
 */
public class TokenPrepareEvent implements PrepareEvent{
    @Override
    public void prepare(Submission submission) {
        JavaParser parser=new JavaParser();
        try {
            TokensMapping tokens=parser.parseTokens(submission.getFiles());
            submission.setSubmissionTokens(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
