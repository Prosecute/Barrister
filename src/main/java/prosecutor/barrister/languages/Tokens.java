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
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.report.logger.LoggerLevel;

import javax.lang.model.type.NullType;
import java.lang.reflect.Field;

public class Tokens {


    public static int GENERIC_TOKEN=1;

    private static java.lang.Class<? extends Tokens>[] tokenClass= new java.lang.Class[]{
            Class.class,Conditions.class,Expressions.class,Statements.class,
            ReturnTypes.class,Blocks.class,Initializations.class,Invocations.class
    };

    public static void generateIndex(){
        L.log(LoggerLevel.DEBUG,"Tokens","Starting generating token indexes.");
        int i=2;
        try {
            for(java.lang.Class<? extends Tokens> clazz:tokenClass)
                i=generateIndexFor(clazz,i);
        } catch (NoSuchFieldException e) {
            L.logException("Tokens", "?\\_(?)_/?", e);
        } catch (IllegalAccessException e) {
            L.logException("Tokens","?\\_(?)_/?",e);
        }
    }

    /**
     * For testing only
     */
    public static void resetIndexs()
    {
        L.log(LoggerLevel.DEBUG,"Tokens","Starting reseting token indexes to 0.");
        for(java.lang.Class<? extends Tokens> clazz:tokenClass)
        {
            Field[] fields=clazz.getDeclaredFields();
            for(Field field:fields)
                try {
                    field.setInt(null,0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }
    private static int generateIndexFor(java.lang.Class<? extends Tokens> clazz,int startIndex) throws NoSuchFieldException, IllegalAccessException {
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
        public static int CLASS_START, CLASS_STOP;
        public static int INTERFACE_START,INTERFACE_STOP;
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
    @Category(name = "Blocks", descriptionL = "token.category.blocks",color = "#AACCAA")
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


    @Category(name = "Return types", descriptionL = "token.category.returns",color = "#8080A0")
    public static class ReturnTypes extends Tokens
    {
        @Description(name = "Return void",descriptionL = "token.returns.void")
        public static int RETURN_VOID;
    }


    @Category(name = "Initializations", descriptionL = "token.category.init", color = "#FF0000")
    public static class Initializations extends Tokens
    {
        public static int ARRAY_INIT_START,ARRAY_INIT_STOP, CONSTANT;
    }

    public static class Null extends Tokens{}

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
    public @interface Description
    {
        java.lang.Class<? extends Tokens> endingFrom() default Null.class;
        String endingName() default "";
        String descriptionL();
        String name();
        String category() default "";
        String color() default "";
    }
    public @interface Category
    {
        String name();
        String descriptionL();
        String color();
    }
}
