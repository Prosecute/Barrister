package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import javax.lang.model.type.NullType;
import java.io.File;

public class ConfigurationFileOption implements Option<NullType> {
    private File parameter;
    @Override
    public char getOptionCharacter() {
        return 'c';
    }

    @Override
    public String getOptionCode() {
        return null;
    }

    @Override
    public String getOptionName() {
        return "Configuration file";
    }

    @Override
    public String getOptionDescription() {
        return "";
    }

    @Override
    public boolean hasDefaultValue() {
        return false;
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.initializationOption;
    }

    @Override
    public NullType getParameter() {
        return null;
    }

    @Override
    public boolean assign(String parameter) {
        this.parameter=new File(parameter);
    }
}
