package cz.cvut.prosecutor.barrister.submissions;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import cz.cvut.prosecutor.barrister.filters.FileFilter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SubmissionsLocation {
    private Path path;

    private boolean direct=false;
    private boolean compareOnly=false;
    private boolean testOnly=false;

    private FileFilter filter;

    public SubmissionsLocation(Path path)
    {
        this.path=path;
    }
    public SubmissionsLocation(Path path,boolean direct,boolean compareOnly,boolean testOnly)
    {
        this(path);
        this.direct=direct;
        this.compareOnly=compareOnly;
        this.testOnly=testOnly;
    }

    public boolean isDirect(){return direct;}
    public boolean isCompareOnly(){return compareOnly;}
    public boolean isTestOnly(){return testOnly;}

    public int submissionCount()
    {
        return getSubmissionsPaths().size();
    }

    public Set<Path> getSubmissionsPaths()
    {
        try {
            DirectoryStream<Path> paths;
            if(direct)
                paths=Files.newDirectoryStream(path,entry -> (Files.isRegularFile(entry)));
            else
                paths=Files.newDirectoryStream(path,entry -> (Files.isDirectory(entry)));
            Set<Path> output=new HashSet<>();
            Iterator<Path> iter=paths.iterator();
            while(iter.hasNext())
                output.add(iter.next());
            return output;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
