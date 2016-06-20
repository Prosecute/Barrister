package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class OutputLocationOption implements Option {
    @Override
    public char getOptionCharacter() {
        return 0;
    }

    @Override
    public String getOptionName() {
        return null;
    }

    @Override
    public String getOptionDescription() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public OptionType getOptionType() {
        return null;
    }

    @Override
    public void assign(String parameter) {

    }
}
