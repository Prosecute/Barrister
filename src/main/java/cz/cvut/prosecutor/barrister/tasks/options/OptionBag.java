package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class OptionBag {

    private final static Option[] allOptions =new Option[]
            {
                    new BaseCodeOption(),
                    new ConfigurationFileOption(),
                    new OutputTypeOption(),
                    new OutputLocationOption()
            };
    public static Option[] getAllOptions()
    {
        return allOptions;
    }
}
