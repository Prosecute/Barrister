package helpers;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class TestConfigurationHelper {


    public static void assertEqualsConfigurations(final ConfigurationType a, final ConfigurationType b)
    {
        //Base assert
        assertEquals(a.getProjectName(),b.getProjectName());
        assertEquals(a.getOutputLocation(),b.getOutputLocation());
        assertEquals(a.getRootDirectory(),b.getRootDirectory());

        //Trials assert
        if(a.getTrials()==null)
            assertNull(b.getTrials());
        else {
            assertEquals(a.getTrials().getTrial().size(),b.getTrials().getTrial().size());
            for(int i=0;i<a.getTrials().getTrial().size();i++)
            {
                TrialConfiguration d=a.getTrials().getTrial().get(i);
                TrialConfiguration c=b.getTrials().getTrial().get(i);
                assertEqualsTrialConfiguration(d,c); // This may cause trouble in future, right now we take trials list as ALWAYS ORDERED.

            }
        }
    }

    public static void assertEqualsTrialConfiguration(final TrialConfiguration a, final TrialConfiguration b)
    {
        if(a==null)
        {
            assertNull(b);
            return;
        }
        assertEquals(a.getTrialID(),b.getTrialID());
        assertEquals(a.getTrialName(),b.getTrialName());
        if(a.getBaseCode()==null)
            assertNull(b.getBaseCode());
        else
        {
            assertEquals(a.getBaseCode().getLocation().size(),b.getBaseCode().getLocation().size());
            for(int i=0;i<a.getBaseCode().getLocation().size();i++)
                assertEquals(a.getBaseCode().getLocation().get(i),b.getBaseCode().getLocation().get(i));
        }
        if(a.getFileFilters()==null)
            assertNull(b.getFileFilters());
        else
        {
            assertEquals(a.getFileFilters().getFileFilter().size(),b.getFileFilters().getFileFilter().size());
            for(int i=0;i<a.getFileFilters().getFileFilter().size();i++)
            {
                FileFilter a1 = a.getFileFilters().getFileFilter().get(i);
                FileFilter b1 = b.getFileFilters().getFileFilter().get(i);
                localAssertEquals(a1.getFileCreateDate(),b1.getFileCreateDate());
                localAssertEquals(a1.getFileModifiedDate(),b1.getFileModifiedDate());
                localAssertEquals(a1.getFileSize(),b1.getFileSize());
                localAssertEquals(a1.getFileExtension(),b1.getFileExtension());
                localAssertEquals(a1.getPath(),b1.getPath());
                assertEquals(a1.getMode(),b1.getMode());
                assertEquals(a1.getName(),b1.getName());
                assertEquals(a1.getRegex(),b1.getRegex());

            }
        }

    }

    private static void localAssertEquals(FilteredList a,FilteredList b)
    {
        if(a==null) {
            assertNull(b);
            return;
        }
        assertEquals(a.getOperation(),b.getOperation());
        assertEquals(a.getElement().size(),b.getElement().size());
        for(int e=0;e<a.getElement().size();e++)
           localAssertEquals(a.getElement().get(e),b.getElement().get(e));
    }

    private static void localAssertEquals(FilteredParameter a,FilteredParameter b)
    {
        if(a==null)
        {
            assertNull(b);
            return;
        }
        assertEquals(a.getOperation(),b.getOperation());
        assertEquals(a.getValue(),b.getValue());
    }
}
