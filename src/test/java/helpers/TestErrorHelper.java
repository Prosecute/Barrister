package helpers;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.Report;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;

public class TestErrorHelper {

    public static void assertEqualsErrors(Report.Errors.Error a, Report.Errors.Error b)
    {
        assertFalse(compareErrors(a,b));
    }
    public static boolean compareErrors(Report.Errors.Error a, Report.Errors.Error b)
    {
        return a.getLevel().equals(b.getLevel())
                && a.getErrorType().equals(b.getErrorType())
                && a.getValue().equals(b.getValue());
    }
}
