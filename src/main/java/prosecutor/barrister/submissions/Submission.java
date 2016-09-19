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
import prosecutor.barrister.submissions.tokens.TokensMapping;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Submission {

    private String name;
    private int submissionLocationID;

    public int getSubmissionLocationID()
    {
        return submissionLocationID;
    }

    public String getName()
    {
        return name;
    }
    public Path getLocation()
    {
        return location;
    }

    private Path location;

    private TokensMapping tokensMapping;

    public Submission(Path submissionLocation)
    {
        location=submissionLocation;
        name=submissionLocation.getName(submissionLocation.getNameCount()-1).toString();
    }
    public Submission(int submissionLocationID,Path submissionLocation)
    {
        location=submissionLocation;
        name=submissionLocation.getName(submissionLocation.getNameCount()-1).toString();
        this.submissionLocationID=submissionLocationID;
    }

    public void setSubmissionTokens(TokensMapping tokens)
    {
        tokensMapping=tokens;
    }
    public TokensMapping getSubmissionTokens()
    {
        return tokensMapping;
    }

    public Set<Path> getFiles() throws IOException {
        Set<Path> output=new HashSet<>();
        return listFiles(output,location);
    }
    Set<Path> listFiles(Set<Path> paths,Path path) throws IOException {
        if(path.toFile().isFile()) {
            paths.add(path);
            return paths;
        }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    paths=listFiles(paths,entry);
                }
                else
                    paths.add(entry);
            }
            }
        return paths;
    }
    public Set<Path> getFiles(PathFilter pathFilter) throws IOException {
        Set<Path> output=getFiles();
        output.removeIf(pathFilter.negate());
        return output;
    }


}
