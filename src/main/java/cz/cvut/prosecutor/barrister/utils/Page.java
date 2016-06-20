package cz.cvut.prosecutor.barrister.utils;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.util.Set;

public interface Page<T> {


    public boolean hasNextPage();

    public Page<T> nextPage();
    public Page<T> firstPage();

    public Set<T> getElements();

}
