package prosecutor.barrister.filters;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.SubmissionsLocation;

import java.util.function.Predicate;

public class SubmissionLocationFilter implements Predicate<SubmissionsLocation> {

    private boolean tested,compared;

    public SubmissionLocationFilter(boolean tested,boolean compared)
    {
        this.tested=tested;
        this.compared=compared;
    }

    public boolean isCompared()
    {
        return compared || tested;
    }
    public boolean isTested()
    {
        return tested;
    }
    public void setTested(boolean tested)
    {
        this.tested=tested;
    }
    public void setCompared(boolean compared)
    {
        this.compared=compared;
    }

    @Override
    public boolean test(SubmissionsLocation submissionsLocation) {
        return (submissionsLocation.isCompared()&&compared)&&submissionsLocation.isTested()&&tested;
    }
}
