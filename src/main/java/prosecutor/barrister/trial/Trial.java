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
import static prosecutor.barrister.Barrister.*;
import prosecutor.barrister.filters.SubmissionLocationFilter;
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.report.logger.Logger;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.tasks.Options;
import prosecutor.barrister.trial.runnable.CompareRunnable;
import prosecutor.barrister.trial.runnable.TokenizeRunnable;
import prosecutor.barrister.utils.Page;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.*;
@Logger(name="Trial")
public class Trial {


    private Language language;

    private int trialID;

    public static Trial getCurrent() {
        return current;
    }
    private static Trial current;

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
        current=this;
        L.logInfo("===================================");
        L.logInfo("    "+R().getString("infoUsing"));
        L.logInfo("CACHE_ACTIVE_LIMIT:     "+Options.CACHE_ACTIVE_LIMIT);
        L.logInfo("CACHE_ONEWAY_LIMIT:     "+Options.CACHE_ONEWAY_LIMIT);
        L.logInfo("RUNTIME_COMPARE_SPLIT:  "+Options.RUNTIME_COMPARE_SPLIT);
        L.logInfo("RUNTIME_THREAD_COUNT:   "+Options.RUNTIME_THREAD_COUNT);
        L.logInfo("RUNTIME_TOKENIZE_SPLIT: "+Options.RUNTIME_TOKENIZE_SPLIT);
        L.logInfo("===================================");
        Page<Submission> tested=manager.getPage(Options.CACHE_ACTIVE_LIMIT, new SubmissionLocationFilter(true,false));
        Page<Submission> compared=manager.getPage(Options.CACHE_ACTIVE_LIMIT, new SubmissionLocationFilter(false,true));
        //Comparing tested<->compared
        //Iterating tested
        int iter_t=0,iter_c=0;
        while (true)
        {
            L.logDebug(MessageFormat.format(R().getString("debugIterationTested"),iter_t));
            if(tested.hasNextPage()) {
                tested = tested.nextPage();
                tokenize(service,tested.getElements());
            }
            else
                break;
            //Iterating compared
            while (true)
            {
                L.logDebug(MessageFormat.format(R().getString("debugIterationCompared"),iter_t,iter_c));
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
                iter_c++;
            }
            iter_c=0;
            iter_t++;
        }


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
