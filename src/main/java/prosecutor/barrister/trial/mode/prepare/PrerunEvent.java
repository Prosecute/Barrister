package prosecutor.barrister.trial.mode.prepare;

import prosecutor.barrister.submissions.Submission;

/**
 * Created by Fry on 05.10.2016.
 */
public interface PrerunEvent {


    public static interface SingleSubmissionPrerunEvent extends PrerunEvent
    {
        public void prerun(Submission tested);
    }
    public static interface MultipleSubmissionPrerunEvent extends PrerunEvent
    {
        public void prerun(Submission tested, Submission compared);
    }
}
