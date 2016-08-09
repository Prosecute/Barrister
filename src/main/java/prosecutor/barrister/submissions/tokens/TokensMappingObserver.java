package prosecutor.barrister.submissions.tokens;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.util.List;
import java.util.TreeSet;

public class TokensMappingObserver {

    TokensMapping tokensMapping;
    TreeSet<Integer> marks=new TreeSet<>();

    public TokensMappingObserver(TokensMapping tokensMapping)
    {
        this.tokensMapping=tokensMapping;
    }

    public List<Integer> getPositionsOfToken(Token token)
    {
        return getPositionsOfToken(token.ID);
    }
    public List<Integer> getPositionsOfToken(int tokenID)
    {
        return tokensMapping.getTokenPosition().get(tokenID);
    }
    public int tokenCount()
    {
        return tokensMapping.tokenList.size();
    }
    public boolean isMarked(int position)
    {
        return marks.contains(position);
    }
    public Token getToken(int position)
    {
        return tokensMapping.tokenList.size()>position?tokensMapping.tokenList.get(position):null;
    }
    public void mark(int position)
    {
        marks.add(position);
    }
    public void unMark(int position)
    {
        marks.remove(position);
    }
    public void markClear()
    {
        marks.clear();
    }
}
