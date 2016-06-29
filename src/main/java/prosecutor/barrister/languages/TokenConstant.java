package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.lang.reflect.Field;

public abstract class TokenConstant {



    public final int[] getAllTokens()
    {
        Field[] fields=this.getClass().getFields();
        int[] values=new int[fields.length];
        int count=0;
        for(Field field:fields)
            try {
                values[count++]=field.getInt(this);
            } catch (IllegalAccessException e) {}
        return values;
    }


}
