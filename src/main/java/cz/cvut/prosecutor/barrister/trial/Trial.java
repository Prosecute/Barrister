package cz.cvut.prosecutor.barrister.trial;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.submissions.Submission;
import cz.cvut.prosecutor.barrister.languages.Language;
import cz.cvut.prosecutor.barrister.utils.Page;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trial {

    public static int CACHE_ONEWAY_LIMIT=20_000;
    public static int CACHE_ACTIVE_LIMIT=1_000;

    public static int RUNTIME_THREAD_COUNT=4;
    public static int RUNTIME_TOKENIZE_SPLIT=100;
    public static int RUNTIME_COMPARE_SPLIT=200;

    private Language language;

    private ExecutorService executorService;

    public void execute()
    {
        executorService=Executors.newFixedThreadPool(RUNTIME_THREAD_COUNT);
        Page<Submission> tested=null;
        Page<Submission> compared=null;

        while (true)
        {
            while (true)
            {

                if(compared.hasNextPage())
                    compared=compared.nextPage();
                else
                {
                    compared=compared.firstPage();
                    break;
                }
            }
            if(tested.hasNextPage())
                tested=tested.nextPage();
            else
                break;
        }


    }

}
