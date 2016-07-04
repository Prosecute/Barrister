package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.tasks.options.OptionBuilderXML;
import prosecutor.barrister.trial.Trial;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class CompareTask extends Task {

    private SubmissionManager submissionManager=new SubmissionManager();
    private Trial[] trials;

    private ExecutorService executorService;

    public SubmissionManager getSubmissionManager()
    {
        return submissionManager;
    }

    public void setTrials(Trial[] trials) {
        this.trials = trials;
    }

    public Trial[] getTrials() {
        return trials;
    }

    @Override
    public String[] getTaskIDs() {
        return new String[]{"compare"};
    }

    @Override
    public String getTaskName() {
        return "Compare";
    }

    @Override
    public String getShortInfo() {
        return null;
    }

    @Override
    public String getLongInfo() {
        return null;
    }

    @Override
    public void run() {
        //Prepare threading
        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue();
        executorService=new ThreadPoolExecutor(Options.RUNTIME_THREAD_COUNT,Options.RUNTIME_THREAD_COUNT,0L, TimeUnit.MILLISECONDS,queue);

        //Execute trials (synchronously)
        for(Trial trial:trials)
        {
            //trial.execute(executorService,queue);
        }

    }

    @Override
    protected void resolveParams(String... params) {
        List<String> list=Arrays.asList(params);
        Iterator<String> iterator=list.iterator();
        while(iterator.hasNext())
        {
            String param=iterator.next();
            if(param.startsWith("-"))
            {
                //TODO
            }
            else
            {
                if(param.endsWith(".xml"))
                {
                    OptionBuilderXML.fromFile(new File(param));
                }
                //TODO
            }
        }
    }
}
