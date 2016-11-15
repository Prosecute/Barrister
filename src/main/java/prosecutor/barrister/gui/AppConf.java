package prosecutor.barrister.gui;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class AppConf {

    private static final File file= new File("confBarrister.ini");
    private Ini ini;
    private AppConf()
    {
        ini=null;
        if(file.exists())
            try {
                ini=new Ini(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            ini=new Ini();
        try {
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String section,String id)
    {
        return ini.get(section,id);
    }
    public <T>T get(String section,String id,Class<T> clazz)
    {
        return ini.get(section,id,clazz);
    }
    public String getOrDefault(String section,String id,String def)
    {
        String ret=ini.get(section,id);
        return ret==null?def:ret;
    }
    public void set(String section,String id,String value)
    {
        ini.put(section,id,value);
        try {
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static AppConf Instance()
    {
        if(instance==null)
            instance=new AppConf();
        return instance;
    }
    private static AppConf instance;

}
