package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public abstract class Task {

    static
    {
        Task[] _tasks=new Task[3];
        _tasks[0]=new HelpTask();
        _tasks[1]=new TasksTask();
        _tasks[2]=new CompareTask();
        Task.tasks=_tasks;
    }

    private static Task[] tasks;

    public static Task resolve(String name)
    {
        for(Task task:tasks)
        {
            for(String id:task.getTaskIDs())
                if(id.equals(name))
                    return task;
        }
        return null;
    }

    public static Task[] getTasks()
    {
        return tasks;
    }

    protected static Task active;

    public static Task getActive()
    {
        return active;
    }
    public final Task setActive()
    {
        Task.active=this;
        Options.resolve();
        return this;
    }


    public abstract String[] getTaskIDs();

    public abstract String getTaskName();

    public abstract String getShortInfo();

    public abstract String getLongInfo();

    public abstract void run();

    protected abstract void resolveParams(String... params);

}
