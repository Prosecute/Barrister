package cz.cvut.prosecutor.barrister.languages.java_default;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.languages.Language;

public class JavaLanguage extends Language {
    @Override
    public String[] defaultSuffix() {
        return new String[]{".java",".jav",".JAVA",".JAV"};
    }

    @Override
    public String name() {
        return "Java";
    }

    @Override
    public String id() {
        return "java";
    }

    @Override
    public String version() {
        return "8";
    }
}
