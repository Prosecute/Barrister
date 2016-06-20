package cz.cvut.prosecutor.barrister.filters;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.submissions.SubmissionsLocation;

import java.util.function.Predicate;

public class SubmissionLocationFilter implements Predicate<SubmissionsLocation> {

    private boolean tested,compared;

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
        return submissionsLocation.isCompareOnly()?compared&&!tested:submissionsLocation.isTestOnly()?tested&&!compared:tested&&compared;
    }
}
