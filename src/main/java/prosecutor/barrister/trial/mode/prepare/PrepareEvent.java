package prosecutor.barrister.trial.mode.prepare;

import prosecutor.barrister.submissions.Submission;

/**
 * Created by Fry on 05.10.2016.
 */
public interface PrepareEvent {

    public void prepare(Submission submission);
}
