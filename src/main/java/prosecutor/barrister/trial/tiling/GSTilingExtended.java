package prosecutor.barrister.trial.tiling;
///////////////////////////////////////////////////////////////////////////////
// Gahler shield
//
// Author: Jiri Fryc
//
// Licence: AGPL v3
//
// This file is part of Barrister, which is part of Prosecutor.
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.submissions.tokens.Token;
import prosecutor.barrister.submissions.tokens.TokensMappingObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GSTilingExtended {


    public void compare(Submission subA, Submission subB, int min)
    {
        TokensMappingObserver observerA=new TokensMappingObserver(subA.getSubmissionTokens()),observerB=new TokensMappingObserver(subB.getSubmissionTokens());


        int maxMatch;
        Stack<Match> matches=new Stack<>(),allMatches=new Stack<>();
        List<Integer> matchingTokenPositions=null;
mainPhase:
        do {
            maxMatch=min;
            matches.clear();
initPhase:
            for(int x=0;x<observerA.tokenCount();x++) {
                if (observerA.isMarked(x) || (matchingTokenPositions = observerB.getPositionsOfToken(observerA.getToken(x))) == null)
                    continue;
                searchingPhase:
                for (Integer matchingTokenPosition : matchingTokenPositions) {
                    if (observerB.isMarked(matchingTokenPosition) || maxMatch > observerB.tokenCount() - matchingTokenPosition)
                        continue;

                    int j, hx, hy;
                    for (j = maxMatch - 1; j >= 0; j--)
                        if (observerA.getToken(hx = x + j).ID != observerB.getToken(hy = matchingTokenPosition + j).ID
                                || observerA.isMarked(hx) || observerB.isMarked(hy))
                            continue searchingPhase;

                    j = maxMatch;
                    while (Token.compareTokenID(observerA.getToken(hx = x + j),observerB.getToken(hy = matchingTokenPosition + j)) && !observerA.isMarked(hx) && !observerB.isMarked(hy))
                        j++;
                    if (j > maxMatch) {
                        maxMatch = j;
                        matches.clear();
                    }
                    matches.push(new Match(x, matchingTokenPosition, j));
                }
            }
            while (!matches.empty())
            {
                Match match=matches.pop();
                int x=match.PositionTokenA;
                int y=match.PositionTokenB;
                for(int j=match.Length;j>0;j--)
                {
                    observerA.mark(x++);
                    observerB.mark(y++);
                }
                allMatches.push(match);
            }

        } while (maxMatch !=min);
        maxMatch++;
        maxMatch--;
    }

    public static class Match
    {
        int PositionTokenA,PositionTokenB,Length;
        public Match()
        {}
        public Match(int posA,int posB,int length)
        {
            this.PositionTokenA=posA;
            this.PositionTokenB=posB;
            this.Length=length;
        }
    }
}
