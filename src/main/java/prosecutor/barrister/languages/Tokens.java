package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import com.sun.org.apache.bcel.internal.generic.RET;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.lang.reflect.Field;

public class Tokens {


    public static int GENERIC_TOKEN=1;

    public static void generateIndex(){

        int i=2;
        try {
            i=generateIndexFor(Class.class,i);
            i=generateIndexFor(Conditions.class,i);
            i=generateIndexFor(Expressions.class,i);
            i=generateIndexFor(Statements.class,i);
            i=generateIndexFor(RETURN.class,i);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private static int generateIndexFor(java.lang.Class clazz,int startIndex) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields=clazz.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            if(fields[i].isAnnotationPresent(Duplicate.class))
            {
                Duplicate duplicate=(Duplicate)fields[i].getAnnotation(Duplicate.class);
                int value=duplicate.from().getDeclaredField(duplicate.name().equals("")?fields[i].getName():duplicate.name()).getInt(null);
                if(value==0) {
                    fields[i].setInt(null, startIndex++);
                    duplicate.from().getDeclaredField(duplicate.name().equals("")?fields[i].getName():duplicate.name()).setInt(null,startIndex-1);
                }

            }
            else if(fields[i].getInt(null)==0)
            {
                fields[i].setInt(null,startIndex++);
            }
        }
        return startIndex;
    }


    public static class Class extends Tokens
    {
        public static int CLASS_CONSTRUCTOR_START , CLASS_CONSTRUCTOR_STOP;
        public static int CLASS_METHOD_START , CLASS_METHOD_STOP;
        public static int ENUM_START, ENUM_STOP, ENUM_CONSTANT;
        public static int PACKAGE;
        public static int IMPORT;
        public static int ANNOTATION;
    }
    public static class Conditions extends Tokens
    {
        public static int IF_START,IF_STOP;

        @Link(from = Conditions.class,name = "DO_WHILE_START",type = Link.LinkType.hard)
        public static int WHILE_START;
        @Link(from = Conditions.class,name = "DO_WHILE_STOP",type = Link.LinkType.hard)
        public static int WHILE_STOP;

        @Link(from = Conditions.class,name = "WHILE_START",type = Link.LinkType.hard)
        public static int DO_WHILE_START;
        @Link(from = Conditions.class,name = "WHILE_STOP",type = Link.LinkType.hard)
        public static int DO_WHILE_STOP;
        public static int FOR_START,FOR_STOP;
        public static int SWITCH_START,SWITCH_STOP;
        public static int SWITCH_CASE;

        public static int CONDITIONAL_EXPRESSION_START;
    }
    public static class Expressions extends Tokens
    {
        @Duplicate(from = Conditions.class)
        public static int CONDITIONAL;
        public static int ASSIGNMENT;
    }

    public static class Statements extends Tokens
    {
        public static int ASSERT;
        public static int CONTINUE;
        public static int RETURN;
        public static int THROW;
        @Duplicate(from = Conditions.class, name = "SWITCH_CASE")
        public static int CASE;
        public static int BREAK;

    }
    public static class Invocations extends Tokens
    {
        public static int INVOKE;
    }
    public static class Blocks extends Tokens
    {
        public static int GENERIC_BLOCK_START,GENERIC_BLOCK_STOP;
        public static int SYNCHRONIZED_START,SYNCHRONIZED_STOP;
        @Link(from = Conditions.class,name = "TRY_WITH_RESOURCE_START",type = Link.LinkType.hard)
        public static int TRY_START;
        @Link(from = Conditions.class,name = "TRY_WITH_RESOURCE_STOP",type = Link.LinkType.hard)
        public static int TRY_STOP;
        @Link(from = Conditions.class,name = "TRY_START",type = Link.LinkType.hard)
        public static int TRY_WITH_RESOURCE_START;
        @Link(from = Conditions.class,name = "TRY_STOP",type = Link.LinkType.hard)
        public static int TRY_WITH_RESOURCE_STOP;
        public static int FINALLY_START,FINALLY_STOP;
        public static int CATCH_START,CATCH_STOP;
    }

    public static class ReturnTypes extends Tokens
    {
        public static int RETURN_VOID;
    }

    public static class Initializations extends Tokens
    {
        public static int ARRAY_INIT_START,ARRAY_INIT_STOP, CONSTANT;
    }


    public @interface Duplicate
    {
        java.lang.Class<? extends Tokens> from();
        String name() default "";
    }
    public @interface Link
    {
        java.lang.Class<? extends Tokens> from();
        String name();
        LinkType type();

        public enum LinkType {hard,soft}
    }
}
