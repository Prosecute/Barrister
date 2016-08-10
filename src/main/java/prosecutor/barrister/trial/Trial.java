package prosecutor.barrister.trial;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.Barrister;
import prosecutor.barrister.filters.SubmissionLocationFilter;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.tasks.Options;
import prosecutor.barrister.trial.runnable.CompareRunnable;
import prosecutor.barrister.trial.runnable.TokenizeRunnable;
import prosecutor.barrister.utils.Page;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.*;

public class Trial {


    private Language language;

    private int trialID;

    public int getTrialID()
    {
        return trialID;
    }
    public void setTrialID(int trialID)
    {
        this.trialID=trialID;
    }

    public void setLanguage(Language language)
    {
        this.language=language;
    }


    public void execute(ExecutorService service,SubmissionManager manager)
    {
        Page<Submission> tested=manager.getPage(Options.CACHE_ACTIVE_LIMIT, new SubmissionLocationFilter(true,false));
        Page<Submission> compared=manager.getPage(Options.CACHE_ACTIVE_LIMIT, new SubmissionLocationFilter(false,true));
        //Comparing tested<->compared
        //Iterating tested
        while (true)
        {
            if(tested.hasNextPage()) {
                tested = tested.nextPage();
                tokenize(service,tested.getElements());
            }
            else
                break;
            //Iterating compared
            while (true)
            {
                if(compared.hasNextPage()) {
                    compared = compared.nextPage();
                }
                else
                {
                    compared=compared.firstPage();
                    break;
                }
                tokenize(service,compared.getElements());
                compare(service,compared.getElements(),tested.getElements());
            }
        }

        //Comparing tested<->tested


    }

    private void tokenize(ExecutorService service,Set<Submission> submissions)
    {
        Collection<Future<?>> futures = new LinkedList<Future<?>>();

        futures.add(service.submit(new TokenizeRunnable(submissions)));

        //Wait for finish (lock)
        for (Future<?> future:futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    private void compare(ExecutorService service,Set<Submission> compared,Set<Submission> tested)
    {
        Collection<Future<?>> futures = new LinkedList<Future<?>>();

        futures.add(service.submit(new CompareRunnable(compared,tested,this)));

        //Wait for finish (lock)
        for (Future<?> future:futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
