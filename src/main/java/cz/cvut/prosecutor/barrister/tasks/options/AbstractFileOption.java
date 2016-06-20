package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.filters.FileFilter;

public abstract class AbstractFileOption implements Option<FileFilter> {


    @Override
    public FileFilter getParameter() {
        return null;
    }

    @Override
    public boolean assign(String parameter) {
        return false;
    }
}
