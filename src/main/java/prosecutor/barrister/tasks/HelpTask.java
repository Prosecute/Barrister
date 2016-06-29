package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class HelpTask extends Task {
    @Override
    public String[] getTaskIDs() {
        return new String[]{"help"};
    }

    @Override
    public String getTaskName() {
        return "Help";
    }

    @Override
    public String getShortInfo() {
        return null;
    }

    @Override
    public String getLongInfo() {
        return null;
    }

    @Override
    public void run() {

    }

    @Override
    protected void resolveParams(String... params) {

    }
}
