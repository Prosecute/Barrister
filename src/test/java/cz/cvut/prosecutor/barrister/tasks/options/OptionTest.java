package cz.cvut.prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.junit.Test;
import static org.junit.Assert.*;

public class OptionTest {

    @Test
    public void testCharUniqueness()
    {
        Option[] options=OptionBag.getAllOptions();
        String usedChars="";
        for(Option option:options)
        {
            assertNotEquals("Char " + option.getOptionCharacter() + " is used multiple times for options.", -1, usedChars.indexOf(option.getOptionCharacter()));
            usedChars+=option.getOptionCharacter();
        }
    }

    @Test
    public void testNameUniqueness()
    {
        Option[] options=OptionBag.getAllOptions();
        for(int first=0;first<options.length;first++)
        {
            Option firstOption=options[first];
            for(int second=0;second<first-1;second++)
            {
                assertNotEquals("Option code " + firstOption.getOptionCode() + " is used multiple times for options.",firstOption.getOptionCode(),options[second].getOptionCode());
            }
        }
    }
}
