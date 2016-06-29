package prosecutor.barrister.trial;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.trial.runnable.TokenizeRunnable;
import prosecutor.barrister.utils.Page;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.*;

public class Trial {


    private Language language;

    public void setLanguage(Language language)
    {
        this.language=language;
    }


    public void execute(ExecutorService service)
    {
        Page<Submission> tested=null;
        Page<Submission> compared=null;

        //Comparing tested<->compared
        //Iterating tested
        while (true)
        {
            tokenize(service,tested.getElements());
            //Iterating compared
            while (true)
            {
                tokenize(service,compared.getElements());
                if(compared.hasNextPage()) {
                    compared = compared.nextPage();
                }
                else
                {
                    compared=compared.firstPage();
                    break;
                }
            }
            if(tested.hasNextPage()) {
                tested = tested.nextPage();
            }
            else
                break;
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
    private void compare(ExecutorService service,Set<Submission> submissions)
    {
        Collection<Future<?>> futures = new LinkedList<Future<?>>();

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
