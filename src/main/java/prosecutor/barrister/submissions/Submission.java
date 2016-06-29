package prosecutor.barrister.submissions;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.filters.PathFilter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Submission {

    private String name;

    private Path location;

    private SubmissionTokens submissionTokens;

    public Submission(Path submissionLocation)
    {
        location=submissionLocation;
        name=submissionLocation.getName(submissionLocation.getNameCount()-1).toString();
    }

    public void setSubmissionTokens(SubmissionTokens tokens)
    {
        submissionTokens=tokens;
    }
    public SubmissionTokens getSubmissionTokens()
    {
        return submissionTokens;
    }

    public Set<Path> getFiles() throws IOException {
        Set<Path> output=new HashSet<>();
        try(DirectoryStream<Path> stream=Files.newDirectoryStream(location,file->Files.isRegularFile(file)))
        {
            for(Path path:stream)
                output.add(path);
        }

        return output;
    }
    public Set<Path> getFiles(PathFilter pathFilter) throws IOException {
        Set<Path> output=getFiles();
        output.removeIf(pathFilter.negate());
        return output;
    }


}
