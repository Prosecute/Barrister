package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class Options {


    public static int CACHE_ONEWAY_LIMIT=20_000;
    public static int CACHE_ACTIVE_LIMIT=1_000;

    public static int RUNTIME_THREAD_COUNT=4;
    public static int RUNTIME_TOKENIZE_SPLIT=100;
    public static int RUNTIME_COMPARE_SPLIT=200;

    private static String[] params;

    protected static void resolve()
    {
        Task.getActive().resolveParams(params);
    }

    public static void load(String... params)
    {
        Options.params=params;
    }

}
