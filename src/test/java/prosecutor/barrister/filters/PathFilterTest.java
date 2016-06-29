package prosecutor.barrister.filters;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.testng.annotations.Test;

import javax.print.DocFlavor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import static org.testng.Assert.*;

public class PathFilterTest {

    @Test(groups = {"unitTest"})
    public void asterixMatching()
    {
        String PATTERN="glob:/data/**";
        String FILE="/data/folder/file.txt";
        String FILE2="/data/folder/";
        String FILE3="/data/";
        PathFilter filter=new PathFilter(PATTERN);
        assertTrue(filter.test(Paths.get(FILE)));
        assertTrue(filter.test(Paths.get(FILE2)));
        assertFalse(filter.test(Paths.get(FILE3)));
    }

    @Test(groups = {"unitTest"})
    public void questionMarkMatching()
    {
        String FOLDER="data/d/";
        String FILE="tes?";
        String TEST="data/d/test/";
        String TEST2="data/d/tast/";
        String TEST3="data/d/testt/";

        String pattern="glob:"+FOLDER+FILE;

        PathFilter filter=new PathFilter(pattern);
        assertTrue(filter.test(Paths.get(TEST)));
        assertFalse(filter.test(Paths.get(TEST2)));
        assertFalse(filter.test(Paths.get(TEST3)));
    }

    @Test(groups = {"unitTest"})
    public void combinePathFilters()
    {
        PathFilter filter=new PathFilter("glob:data/t?st");
        PathFilter filter2=new PathFilter("glob:data/te*");

        PathFilter filter3=new PathFilter("glob:data/?est");

        String TEST="data/test";
        String TEST2="data/tast";
        String TEST3="data/terr";
        String TEST4="data/dest";

        Predicate<Path> combined=filter.or(filter2).and(filter3.negate());
        assertFalse(combined.test(Paths.get(TEST)));
        assertTrue(combined.test(Paths.get(TEST2)));
        assertTrue(combined.test(Paths.get(TEST3)));


        Predicate<Path> combined2=filter.and(filter2).or(filter3.negate());
        assertTrue(combined2.test(Paths.get(TEST)));
        assertTrue(combined2.test(Paths.get(TEST2)));
        assertTrue(combined2.test(Paths.get(TEST3)));
        assertFalse(combined2.test(Paths.get(TEST4)));
    }
}
