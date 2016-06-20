package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.io.File;

public class BaseCodeOption implements Option {
    private File parameter;

    @Override
    public char getOptionCharacter() {
        return 'b';
    }

    @Override
    public String getOptionName() {
        return "Base code";
    }

    @Override
    public String getOptionDescription() {
        return "Location of base code.";
    }

    @Override
    public boolean isValid() {
        return parameter.exists();
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.engineOption;
    }

    @Override
    public void assign(String parameter) {
        this.parameter=new File(parameter);
    }
}
