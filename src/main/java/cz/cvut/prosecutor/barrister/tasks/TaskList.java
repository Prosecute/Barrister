package cz.cvut.prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class TaskList {
    static
    {
        Task[] _tasks=new Task[2];
        _tasks[0]=new HelpTask();
        _tasks[1]=new TasksTask();
        TaskList.tasks=_tasks;
    }

    private TaskList()
    {

    }
    private static Task[] tasks;

    public static Task[] getTasks()
    {
        return tasks;
    }

}
