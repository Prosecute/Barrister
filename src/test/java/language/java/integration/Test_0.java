package language.java.integration;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////

import language.DefaultIntegrationTest;
import org.testng.annotations.Test;

@Test(singleThreaded = true, groups = "integration", testName = "Java integration test 0")
public class Test_0 extends DefaultIntegrationTest {


    public Test_0() {
        super("src/test/resources/language/java/integration/test_0/barrister_test.xml");
    }

    @Test()
    public void matchTest()
    {

    }

}
