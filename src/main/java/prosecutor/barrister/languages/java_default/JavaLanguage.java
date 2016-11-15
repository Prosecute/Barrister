package prosecutor.barrister.languages.java_default;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.languages.Language;
import prosecutor.barrister.languages.Parser;

public class JavaLanguage extends Language {
    @Override
    public String[] defaultExtensions() {
        return new String[]{"java","jav","JAVA","JAV"};
    }

    @Override
    public Parser getParser() {
        return new JavaParser();
    }
}
