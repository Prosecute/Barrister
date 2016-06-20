package cz.cvut.prosecutor.barrister.filters;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;

public class PathFilter implements Predicate<Path> {
    @Override
    public boolean test(Path path) {
        return false;
    }
}
