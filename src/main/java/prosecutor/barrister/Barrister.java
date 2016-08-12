package prosecutor.barrister;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.languages.Tokens;
import prosecutor.barrister.tasks.Options;
import prosecutor.barrister.tasks.Task;

import java.util.Arrays;

public class Barrister {
    public static String NAME="Barrister";
    public static String VERSION="0.1alpha";

    private static void response404()
    {
        Task.resolve("help").setActive().run();
    }

    public static void main(String... args)
    {
        if(args==null || args.length==0)
        {
            response404();
            return;
        }
        else if(args.length>1)
            Options.load(Arrays.copyOfRange(args,1,args.length));

        Tokens.generateIndex();
        Task task=Task.resolve(args[0]);
        if(task!=null)
            task.setActive().run();
        else
            response404();

    }
}
