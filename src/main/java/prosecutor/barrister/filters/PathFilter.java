package prosecutor.barrister.filters;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.function.Predicate;

public class PathFilter implements Predicate<Path> {
    PathMatcher matcher;
    public PathFilter(String pattern)
    {
        matcher=FileSystems.getDefault().getPathMatcher(pattern);
    }
    @Override
    public boolean test(Path path) {
        return matcher.matches(path);
    }
}
