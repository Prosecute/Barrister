package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class Tokens {

    public void test()
    {
    }


    public static class Class
    {
        public static int CLASS_CONSTRUCTOR_START , CLASS_CONSTRUCTOR_END;
        public static int CLASS_METHOD_START , CLASS_METHOD_END;
    }
    public static class Conditions
    {
        public static int IF_START,IF_STOP;
        public static int WHILE_START,WHILE_STOP;
        public static int FOR_START,FOR_STOP;
        public static int SWITCH_START,SWITCH_STOP;

        public static int CONDITIONAL_EXPRESSION_START;
    }
    public static class Expressions
    {
        @Duplicate(from = Conditions.class)
        public static int CONDITIONAL_EXPRESSION_START;
        public static int ASSIGNMENT_EXPRESSION_START;
    }

    public static class Statements
    {
        public static int ASSERT;
        public static int CONTINUE;
        public static int RETURN;
        public static int THROW;

        public static int SYNCHRONIZED_START,SYNCHRONIZED_STOP;
    }

    public static class ReturnTypes
    {
        public static int RETURN_VOID;
    }

    public @interface Duplicate
    {
        java.lang.Class from();
        String name() default "";
    }
}
