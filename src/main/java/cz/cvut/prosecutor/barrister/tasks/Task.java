package cz.cvut.prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public interface Task {

    public String[] getTaskIDs();

    public String getTaskName();

    public String getShortInfo();

    public String getLongInfo();

    public void run();

}
