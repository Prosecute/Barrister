package prosecutor.barrister.report.logger;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.ConsoleLine;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class L {



    protected static void log(LoggerFlag flag,LoggerLevel level,String source,String message)
    {
        ConsoleLine line=new ConsoleLine();
        line.setLevel(level.name().toUpperCase());
        line.setSource(source);
        line.setTime(BigInteger.valueOf(System.currentTimeMillis()/1000L));
        line.setValue(message);
    }
}
