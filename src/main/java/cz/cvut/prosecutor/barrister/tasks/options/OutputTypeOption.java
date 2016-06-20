package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class OutputTypeOption implements Option<OutputTypeOption.OutputType>{
    @Override
    public char getOptionCharacter() {
        return 'O';
    }

    @Override
    public String getOptionCode() {
        return null;
    }

    @Override
    public String getOptionName() {
        return "OutputType";
    }

    @Override
    public String getOptionDescription() {
        return null;
    }

    @Override
    public boolean hasDefaultValue() {
        return false;
    }


    @Override
    public OptionType getOptionType() {
        return null;
    }

    @Override
    public OutputType getParameter() {
        return null;
    }

    @Override
    public boolean assign(String parameter) {
        return false;
    }

    public enum OutputType
    {
        xml
    }
}
