package prosecutor.barrister.languages;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.tokens.TokensMapping;

import java.nio.file.Path;
import java.util.Set;

public abstract class Parser {

    protected int errors=0;

    public boolean hasError()
    {
        return errors>0;
    }
    public int getErrorsSum()
    {
        return errors;
    }

    public abstract TokensMapping parseTokens(Set<Path> files);

    public static class ParserException extends Exception
    {}

}
