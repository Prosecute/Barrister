package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.testng.annotations.Test;
import prosecutor.barrister.Barrister;

public class CompareTaskTest {

    @Test
    public void integrateTest()
    {
        String CMD="compare src/test/resources/inputConfigurations/inputConf0.xml";
        Barrister.main(CMD.split(" "));
    }
}
