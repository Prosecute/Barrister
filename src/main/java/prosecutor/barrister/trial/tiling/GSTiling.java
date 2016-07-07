package prosecutor.barrister.trial.tiling;
///////////////////////////////////////////////////////////////////////////////
//
//Author: JPLAG TEAM (https://github.com/jplag/jplag)
//
//Licence: GPL v3
//
//This file is part of JPLAG.
///////////////////////////////////////////////////////////////////////////////

public class GSTiling {


/*
    public int TODO_REM=20;

    public void createHashes(SubmissionTokens tokens, int hashLength, boolean makeTable)
    {
        if (hashLength<1) hashLength = 1;
        hashLength = (hashLength<26 ? hashLength : 25);

        if (tokens.size()<hashLength) return;

        int modulo = ((1 << 6) - 1);   // Modulo 64!

        int loops = tokens.size()-hashLength;
        tokens.table = (makeTable ? new GSTilingTable(3*loops) : null);
        int hash = 0;
        int i;
        int hashedLength = 0;
        for (i=0; i<hashLength; i++) {
            hash = (2*hash) + (tokens.tokens(i).Token & modulo);
            hashedLength++;
            if (tokens.tokens(i).marked)
                hashedLength = 0;
        }
        int factor = (hashLength != 1 ? (2<<(hashLength-2)) : 1);

        if (makeTable) {
            for (i=0; i<loops; i++) {
                if (hashedLength >= hashLength) {
                    tokens.tokens(i).hash = hash;
                    tokens.table.add(hash, i);   // add into hashtable
                } else
                    tokens.tokens(i).hash = -1;
                hash -= factor * (tokens.tokens(i).Token & modulo);
                hash = (2*hash) + (tokens.tokens(i+hashLength).Token & modulo);
                if (tokens.tokens(i+hashLength).marked)
                    hashedLength = 0;
                else
                    hashedLength++;
            }
        } else {
            for (i=0; i<loops; i++) {
                tokens.tokens(i).hash = (hashedLength >= hashLength) ? hash : -1;
                hash -= factor * (tokens.tokens(i).Token & modulo);
                hash = (2*hash) + (tokens.tokens(i+hashLength).Token & modulo);
                if (tokens.tokens(i+hashLength).marked)
                    hashedLength = 0;
                else
                    hashedLength++;
            }
        }
        tokens.hashLength = hashLength;
    }
    public final AllMatches compare(Submission subA, Submission subB) {
        Submission A, B, tmp;
        if (subA.getSubmissionTokens().size() > subB.getSubmissionTokens().size()) {
            A = subB;  B = subA;
        } else {
            A = subB;  B = subA;
        }
        // if hashtable exists in first but not in second structure: flip around!
        if (B.getSubmissionTokens().table == null && A.getSubmissionTokens().table != null) {
            tmp = A;
            A = B;
            B = tmp;
        }

        return compare(A, B,TODO_REM);
    }
    private final AllMatches compare(Submission subA, Submission subB, int mml) {
        SubmissionTokens structA = subA.getSubmissionTokens();
        SubmissionTokens structB = subB.getSubmissionTokens();

        // FILE_END used as pivot

        // init
        SubmissionTokens.SubmissionToken[] A = structA.tokens();
        SubmissionTokens.SubmissionToken[] B = structB.tokens();
        int lengthA = structA.size()-1;  // minus pivots!
        int lengthB = structB.size()-1;  // minus pivots!
        TokenMatchPipe matches=new TokenMatchPipe();
        AllMatches allMatches = new AllMatches(subA,subB);

        if (lengthA < mml || lengthB < mml)
            return allMatches;

        // Initialize
        if(!program.useBasecode()) {
            for(int i = 0; i <= lengthA; i++)
                A[i].marked = A[i].type == FILE_END || A[i].type == SEPARATOR_TOKEN;

            for(int i = 0; i <= lengthB; i++)
                B[i].marked = B[i].type == FILE_END || B[i].type == SEPARATOR_TOKEN;
        } else {
            for(int i = 0; i <= lengthA; i++)
                A[i].marked = A[i].type == FILE_END || A[i].type == SEPARATOR_TOKEN || A[i].basecode;

            for(int i = 0; i <= lengthB; i++)
                B[i].marked = B[i].type == FILE_END || B[i].type == SEPARATOR_TOKEN || B[i].basecode;
        }

        // start:
        if (structA.hashLength != TODO_REM) {
            createHashes(structA, mml, false);
        }
        if (structB.hashLength != TODO_REM
                || structB.table == null) {
            createHashes(structB, mml, true);
        }

        int maxmatch;
        int[] elemsB;

        do {
            maxmatch = mml;
            matches.clear();
            for (int x = 0; x <= lengthA - maxmatch; x++) {
                if (A[x].marked || A[x].hash == -1
                        || (elemsB = structB.table.get(A[x].hash)) == null)
                    continue;
                inner:			for (int i = 1; i <= elemsB[0]; i++) { // elemsB[0] contains the length of the Array
                    int y = elemsB[i];
                    if (B[y].marked || maxmatch > lengthB - y) continue;

                    int j, hx, hy;
                    for (j = maxmatch - 1; j >= 0; j--) { //begins comparison from behind
                        if (A[hx = x + j].Token != B[hy = y + j].Token || A[hx].marked || B[hy].marked)
                            continue inner;
                    }

                    // expand match
                    j = maxmatch;
                    while(A[hx = x + j].Token == B[hy = y + j].Token && !A[hx].marked && !B[hy].marked)
                        j++;

                    if (j > maxmatch) {  // new biggest match? -> delete current smaller
                        matches.clear();
                        maxmatch = j;
                    }
                    matches.addMatch(x, y, j);  // add match
                }
            }
            for (int i = matches.size() - 1; i >= 0; i--) {
                int x = matches.matches[i].startA;  // begining of sequence A
                int y = matches.matches[i].startB;  // begining of sequence B
                allMatches.addMatch(x, y, matches.matches[i].length);
                //in order that "Match" will be newly build     (because reusing)
                for (int j = matches.matches[i].length; j > 0; j--)
                    A[x++].marked = B[y++].marked = true;   // mark all Token!
            }

        } while (maxmatch != mml);

        return allMatches;
    }

*/
}