package prosecutor.barrister.submissions;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.utils.Page;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

public class SubmissionPage implements Page<Submission> {

    List<SubmissionsLocation> locationList;
    Queue<Path> buffered=new LinkedTransferQueue<>();
    int locationPosition=0;
    int maxSize;

    Set<Submission> submissions=new HashSet<>();


    public SubmissionPage(List<SubmissionsLocation> locationList, int maxSize)
    {
        this.locationList=locationList;
        this.maxSize=maxSize;
    }
    public SubmissionPage(List<SubmissionsLocation> locationList, int maxSize, int locationPosition)
    {
        this(locationList,maxSize);
        this.locationPosition=locationPosition;
    }

    @Override
    public boolean hasNextPage() {
        return buffered.size()>0||locationList.size()>locationPosition;
    }

    @Override
    public Page<Submission> nextPage() {
        submissions=new HashSet<>();
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
