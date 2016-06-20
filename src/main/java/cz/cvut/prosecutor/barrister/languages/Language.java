package cz.cvut.prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public abstract class Language {

    public abstract String[] defaultSuffix();

    protected String[] suffix;
    public final String[] suffix()
    {
        if(suffix==null)
            suffix=defaultSuffix();
        return suffix;
    }

    public void setSuffix(String[] _suffix)
    {
        this.suffix=_suffix;
    }


    public abstract String name();

    public abstract String id();

    public abstract String version();

    public String[] supportedVersions()
    {
        return new String[]{version()};
    }
    public String[] unsupportedVersions()
    {
        return new String[]{};
    }



}
