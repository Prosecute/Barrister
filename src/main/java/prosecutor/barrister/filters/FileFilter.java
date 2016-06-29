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
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class FileFilter implements Predicate<File> {

    private List<PathMatcher> matchers=new ArrayList<>();



    public boolean accept(File file)
    {
        for(PathMatcher matcher:matchers)
            if(!matcher.matches(file.toPath()))
                return false;
        return true;
    }
    public Set<File> filter(Set<File> files)
    {
        Set<File> output=new HashSet<>();
        for(File file:files)
            if(accept(file))
                output.add(file);
        return output;
    }

    public FileFilter()
    {
    }
    public void addMatcher(String pattern)
    {
        matchers.add(FileSystems.getDefault().getPathMatcher(pattern));
    }

    @Override
    public boolean test(File file) {
        return false;
    }

}
