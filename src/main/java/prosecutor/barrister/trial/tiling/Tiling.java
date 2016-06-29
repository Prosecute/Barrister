package prosecutor.barrister.trial.tiling;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.submissions.Submission;

public interface Tiling {

    public void compare(Submission subA, Submission subB);
}
