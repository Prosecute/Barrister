package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public interface Option<T> {

    public char getOptionCharacter();

    public String getOptionCode();

    public String getOptionName();

    public String getOptionDescription();

    public boolean hasDefaultValue();

    public OptionType getOptionType();

    public T getParameter();

    public boolean assign(String parameter);

}
