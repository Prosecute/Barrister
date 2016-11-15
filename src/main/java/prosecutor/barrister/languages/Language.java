package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.languages.java_default.JavaLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Language {
    static
    {
        List<LanguageWrapper> _wrappers=new ArrayList<>();
        LanguageWrapper wrapper=new LanguageWrapper(JavaLanguage.class,"java","Java","1.8");
        wrapper.setSupportedVersions("1.7","1.6","1.5","1.4","1.3","1.2","8","7","6","5","4","3","2");
        wrapper.setUnsupportedVersions("1.9","9");

        _wrappers.add(wrapper);
        Language.languageWrappers=_wrappers;
    }

    private static List<LanguageWrapper> languageWrappers;

    public static String[] getLanguageCodes()
    {
        String[] s=new String[languageWrappers.size()];
        for(int i=0;i<languageWrappers.size();i++)
        {
            s[i]=languageWrappers.get(i).id();
        }
        return s;
    }

    public static Language resolve(String name,String version)
    {
        LanguageWrapper candidate=null;
        for(LanguageWrapper wrapper:languageWrappers)
        {
            if(!wrapper.id().equals(name.trim()))
                continue;
            if(version==null)
                return  wrapper.createLanguage();
            if(version.trim().equals(wrapper.version()))
                return wrapper.createLanguage();
            if(Arrays.stream(wrapper.unsupportedVersions()).filter(ver -> ver.equals(version)).count()>0)
                continue;
            if(Arrays.stream(wrapper.supportedVersions()).filter(ver -> ver.equals(version)).count()>0)
                return wrapper.createLanguage();
            if(candidate==null)
                candidate=wrapper;
        }
        if(candidate!=null)
            return candidate.createLanguage();
        return null;
    }



    public abstract String[] defaultExtensions();

    protected String[] extensions;
    public final String[] extensions()
    {
        if(extensions==null)
            extensions=defaultExtensions();
        return extensions;
    }

    public abstract Parser getParser();

    public void setExtensions(String[] _extensions)
    {
        this.extensions=_extensions;
    }




}
