package prosecutor.barrister.submissions.tokens;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class Token implements Comparable<Token> {

    public int ID=-1;
    public int Line=-1;
    public int Position=-1;
    public int Length=-1;

    public Token(int ID, int Line, int Position, int Length)
    {
        this(ID,Line,Position);
        this.Length=Length;
    }
    public Token(int ID, int Line, int Position)
    {
        this(ID,Line);
        this.Position=Position;
    }
    public Token(int ID, int Line)
    {
        this(ID);
        this.Line=Line;
    }
    public Token(int ID)
    {
        this.ID=ID;
    }
    public Token(){}

    @Override
    public int compareTo(Token o) {
        return this.Line-o.Line;
    }

    @Override
    public String toString() {
        return "Token[ tokenID="+ID+", line="+Line+", position="+Position+", length="+Length+" ]";
    }
}
