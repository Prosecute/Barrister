package cz.cvut.prosecutor.barrister.utils;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public interface PageProviderFiltered<T,F> extends PageProvider {

    public Page<T> getPage(int size,F filter);
}
