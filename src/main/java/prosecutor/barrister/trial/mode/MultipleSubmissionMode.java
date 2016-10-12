package prosecutor.barrister.trial.mode;

import prosecutor.barrister.submissions.Submission;

/**
 * Created by Fry on 04.10.2016.
 */
public interface MultipleSubmissionMode {
    public void run(Submission tested,Submission compared);
}
