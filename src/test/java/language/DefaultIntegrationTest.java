package language;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import helpers.TestConfigurationHelper;
import helpers.TestErrorHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import prosecutor.barrister.jaxb.ConfigurationType;
import prosecutor.barrister.jaxb.Report;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public abstract class DefaultIntegrationTest extends AbstractIntegrationTest {
    public DefaultIntegrationTest(String configurationLocation) {
        super(configurationLocation);
    }
    @DataProvider(name = "errorListProvider")
    public Object[][] errorListProvider()
    {
        return new Object[][]{{null,false}};
    }
    @DataProvider(name = "matchListProvider")
    public Object[][] matchListProvider()
    {
        return new Object[][]{{null,false}};
    }

    @Test(groups = "validation", dependsOnGroups = {"compare"})
    public void resultConfigurationTest()
    {
        assertNotNull(report);
        assertNotNull(report.getConfiguration());
        ConfigurationType a=report.getConfiguration();
        ConfigurationType b=configuration;
        TestConfigurationHelper.assertEqualsConfigurations(a,b); // All testing logic inside.
    }

    @Test(groups = "validation", dependsOnGroups = {"compare"}, dataProvider = "errorListProvider",
            description = "Validating errors from result.")
    public void errorTest(List<Report.Errors.Error> errorList, boolean strict)
    {
        if(errorList==null)
        {
            assertTrue(report.getErrors()==null && report.getErrors().getError().size()==0);
            return;
        }
        List<Report.Errors.Error> data=report.getErrors().getError();
        assertEquals(data.size(),errorList.size());
        if(strict)
        {
            for(int i=0;i<data.size();i++)
                TestErrorHelper.assertEqualsErrors(data.get(i),errorList.get(i));
        }
        else
        {
            errorList=Lists.newArrayList(errorList);
            for(Report.Errors.Error error:data)
            {
                Iterator<Report.Errors.Error> iter=errorList.listIterator();
                while (iter.hasNext())
                {
                    if(TestErrorHelper.compareErrors(iter.next(),error))
                        iter.remove();
                }
            }
            assertEquals(errorList.size(),0);
        }
    }


}
