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

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class SubmissionPage implements Page<Submission> {

    List<SubmissionsLocation> locationList;
    Queue<Path> buffered=new LinkedTransferQueue<>();
    int locationPosition=0;
    int maxSize;

    Set<Submission> submissions=new HashSet<>();

    @Override
    public boolean hasNextPage() {
        return buffered.size()>0||locationList.size()>locationPosition;
    }

    @Override
    public Page<Submission> nextPage() {
        submissions.clear();
        if(buffered.size()<maxSize && locationList.size()>locationPosition)
        {
            SubmissionsLocation location=locationList.get(locationPosition++);
            buffered.addAll(location.getSubmissionsPaths());
        }
        for(int i=(buffered.size()<maxSize?buffered.size():maxSize);i>0;i--)
            submissions.add(new Submission(buffered.poll()));


        return this;
    }

    @Override
    public Page<Submission> firstPage() {
        locationPosition=0;
        nextPage();
        return this;
    }

    @Override
    public Set<Submission> getElements() {
        return submissions;
    }
}
