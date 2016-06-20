package cz.cvut.prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public abstract class Parser {

    protected int errors=0;

    public boolean hasError()
    {
        return errors>0;
    }
    public int getErrorsSum()
    {
        return errors;
    }

}
