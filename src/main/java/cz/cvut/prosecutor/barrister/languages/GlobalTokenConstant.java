package cz.cvut.prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class GlobalTokenConstant extends TokenConstant {


    final public short FILE_END = 0;
    final public short SEPARATOR_TOKEN = 1;

    final public short PACKAGE = 2 ;
    final public short IMPORT = 3 ;
    final public short CLASS_BEGIN = 4 ;
    final public short CLASS_END = 5 ;
    final static short METHOD_BEGIN = 6;
    final static short METHOD_END = 7;
    final static short VARDEF = 8;
    final static short SYNC_BEGIN = 9;
    final static short SYNC_END = 10;
    final static short DO_BEGIN = 11;
    final static short DO_END = 12;
    final static short WHILE_BEGIN = 13;
    final static short WHILE_END = 14;
    final static short FOR_BEGIN = 15;
    final static short FOR_END = 16;
    final static short SWITCH_BEGIN = 17;
    final static short SWITCH_END = 18;
    final static short CASE = 19;
    final static short TRY_BEGIN = 20;
    final static short CATCH_BEGIN = 21;
    final static short CATCH_END = 22;
    final static short FINALLY = 23;
    final static short IF_BEGIN = 24;
    final static short ELSE = 25;
    final static short IF_END = 26;
    final static short COND = 27;
    final static short BREAK = 28;
    final static short CONTINUE = 29;
    final static short RETURN = 30;
    final static short THROW = 31;
    final static short IN_CLASS_BEGIN = 32;
    final static short IN_CLASS_END = 33;
    final static short APPLY = 34;
    final static short NEWCLASS = 35;
    final static short NEWARRAY = 36;
    final static short ASSIGN = 37;
    final static short shortERFACE_BEGIN = 38;
    final static short shortERFACE_END = 39;
    final static short CONSTR_BEGIN = 40;
    final static short CONSTR_END = 41;
    final static short INIT_BEGIN = 42;
    final static short INIT_END = 43;
    final static short VOID = 44;
    final static short ARRAY_INIT_BEGIN = 45;
    final static short ARRAY_INIT_END = 46;

}
