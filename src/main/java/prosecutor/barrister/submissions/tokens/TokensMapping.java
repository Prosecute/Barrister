package prosecutor.barrister.submissions.tokens;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.util.*;

public class TokensMapping {

    protected List<FileMeta> filesMapping=new ArrayList<>();

    protected List<Token> tokenList=new ArrayList<>();

    //HACK: Will be removed after solving links between tokens.
    private Map<Integer,List<Integer>> tokenPosition;
    protected Map<Integer,List<Integer>> getTokenPosition() {
        if (tokenPosition == null) {
            tokenPosition = new HashMap<>();
            for(Token token:tokenList)
                if(tokenPosition.containsKey(token.ID))
                    tokenPosition.get(token.ID).add(tokenList.size());
                else
                    tokenPosition.put(token.ID,new ArrayList<>(Collections.singletonList(tokenList.size())));
        }

        return tokenPosition;
    }

    public TokensMappingInserter getInserter(String filename)
    {
        return new TokensMappingInserter(this,filename);
    }

    public void addToken(String file,Token token)
    {
        if(filesMapping.isEmpty() || !filesMapping.get(filesMapping.size()).Name.equals(file))
            filesMapping.add(new FileMeta(file,tokenList.size()));

        tokenPosition=null;

        tokenList.add(token);

    }



    private class FileMeta
    {
        protected String Name;
        protected int FromToken;

        protected FileMeta(String name,int from)
        {
            this.Name=name;
            this.FromToken=from;
        }
    }
    public class TokensMappingInserter
    {
        private TokensMapping mapping;
        private String fileID;
        protected TokensMappingInserter(TokensMapping mapping,String fileID)
        {
            this.mapping=mapping;
            this.fileID=fileID;
        }
        public void addToken(int tokenID, org.antlr.v4.runtime.Token token)
        {
            mapping.addToken(fileID,new Token(tokenID,token.getLine(),token.getCharPositionInLine(),token.getText().length()));
        }
    }
}
