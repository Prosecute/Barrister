package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TasksTask extends Task {
    @Override
    public String[] getTaskIDs() {
        return new String[]{"tasks"};
    }

    @Override
    public String getTaskName() {
        return "Tasks";
    }

    @Override
    public String getShortInfo() {
        return "Show all available tasks.";
    }

    @Override
    public String getLongInfo() {
        return "Show all available tasks.";
    }

    @Override
    public void run() {
        Logger logger= LogManager.getLogger("Task");
        logger.info("Tasks:");
        int counter=0;
        for(Task task:Task.getTasks())
        {
            counter++;
            logger.info("  "+task.getTaskIDs()[0]+"   "+task.getTaskName()+" - "+task.getShortInfo());
        }
    }

    @Override
    protected void resolveParams(String... params) {

    }
}
