package prosecutor.barrister.submissions;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.antlr.v4.runtime.Token;

import java.util.*;

public class SubmissionTokens {

    Map<String,List<SubmissionToken>> mapped=new HashMap<>();


    public void addToken(String file,SubmissionToken token)
    {
        if(!mapped.containsKey(file))
            mapped.put(file,new ArrayList<>());
        mapped.get(file).add(token);
    }
    public void setTokensForFile(String file,List<SubmissionToken> tokenList)
    {
        mapped.put(file,tokenList);
    }
    public void sort()
    {
        mapped.values().forEach(list-> Collections.sort(list));
    }

    public SubmissionFileTokens getSubmissionFileTokens(String filename)
    {
        return new SubmissionFileTokens(filename,this);
    }

    public static class SubmissionFileTokens
    {
        private String filename;
        private SubmissionTokens submissionTokens;
        private SubmissionFileTokens(String filename,SubmissionTokens submissionTokens)
        {
            this.filename=filename;
            this.submissionTokens=submissionTokens;
        }
        public void addToken(SubmissionToken token)
        {
            submissionTokens.addToken(filename,token);
        }
        public void addToken(int tokenIndex,Token token)
        {
            this.addToken(new SubmissionToken(tokenIndex, token.getLine(), token.getStartIndex(), token.getText().length()));
        }
    }

    public static class SubmissionToken implements Comparable<SubmissionToken>
    {
        public int Token=-1;
        public int Line=-1;
        public int Position=-1;
        public int Length=-1;

        public SubmissionToken(int Token, int Line, int Position, int Length)
        {
            this(Token,Line,Position);
            this.Length=Length;
        }
        public SubmissionToken(int Token, int Line, int Position)
        {
            this(Token,Line);
            this.Position=Position;
        }
        public SubmissionToken(int Token, int Line)
        {
            this(Token);
            this.Line=Line;
        }
        public SubmissionToken(int Token)
        {
            this.Token=Token;
        }
        public SubmissionToken(){}

        @Override
        public int compareTo(SubmissionToken o) {
            return this.Line-o.Line;
        }

        @Override
        public String toString() {
            return "Token[ tokenID="+Token+", line="+Line+", position="+Position+", length="+Length+" ]";
        }
    }
}
