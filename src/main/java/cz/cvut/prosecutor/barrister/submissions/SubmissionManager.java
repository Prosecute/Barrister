package cz.cvut.prosecutor.barrister.submissions;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.filters.SubmissionLocationFilter;
import cz.cvut.prosecutor.barrister.utils.Page;
import cz.cvut.prosecutor.barrister.utils.PageProviderFiltered;

import java.util.ArrayList;
import java.util.List;

public class SubmissionManager implements PageProviderFiltered<Submission,SubmissionLocationFilter> {

    private int testedSubmissions=0;
    private int comparedSubmissions=0;
    protected List<SubmissionsLocation> locationList=new ArrayList<>();

    public void addLocation(SubmissionsLocation location)
    {
        locationList.add(location);
    }


    public int getSubmissionCount()
    {
        return testedSubmissions+comparedSubmissions;
    }
    public int getTestedSubmissionCount()
    {
        return testedSubmissions;
    }
    public int getComparedSubmissionCount() { return comparedSubmissions; }


    @Override
    public Page getPage(int size, SubmissionLocationFilter filter) {
        return null;
    }

    @Override
    public Page getPage(int size) {
        return null;
    }
}
