package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////




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
    public String[] getLongInfo() {
        return null;
    }

    @Override
    public void run() {
        System.out.println("Tasks:");
        int counter=0;
        for(Task task:Task.getTasks())
        {
            counter++;
            System.out.println("  " + task.getTaskIDs()[0] + "                  ".substring(task.getTaskIDs()[0].length()) + task.getTaskName() + "                  ".substring(task.getTaskName().length())+ task.getShortInfo());
        }
    }

    @Override
    protected void resolveParams(String... params) {

    }
}
