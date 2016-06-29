package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.util.Arrays;

public class LanguageWrapper {

    private String name,id,version;
    private Class<? extends Language> aClass;
    private String[] supportedVersions,unsupportedVersions;

    public LanguageWrapper(Class<? extends Language> aClass,String id, String name, String version)
    {
        this.id=id;
        this.name=name;
        this.version=version;
        this.aClass=aClass;
    }

    public LanguageWrapper setSupportedVersions(String... supportedVersions)
    {
        this.supportedVersions=Arrays.copyOf(supportedVersions,supportedVersions.length+1);
        this.supportedVersions[supportedVersions.length]=version;
        return this;
    }
    public LanguageWrapper setUnsupportedVersions(String... unsupportedVersions)
    {
        this.unsupportedVersions=unsupportedVersions;
        return this;
    }

    public String name() {  return name; }

    public String id() {    return id;  }

    public String version() {   return version; }

    public Language createLanguage()
    {
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace(); //TODO
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] supportedVersions()
    {
        if(supportedVersions==null)
            return new String[]{version()};
        else
            return supportedVersions;
    }
    public String[] unsupportedVersions()
    {
        if(unsupportedVersions==null)
            return new String[]{};
        else
            return unsupportedVersions;
    }
}
