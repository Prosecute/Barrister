package prosecutor.barrister.report.logger;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


public class Logger {

    private String source;
    private LoggerFlag flag;

    protected Logger(String source)
    {
        this.source="["+source+"]";
    }

    public void log(LoggerLevel level,String msg)
    {
        L.log(flag,level,source,msg);
    }
    public void logInfo(String msg)
    {
        log(LoggerLevel.INFO,msg);
    }
    public void logWarn(String msg)
    {
        log(LoggerLevel.WARN,msg);
    }
    public void logError(String msg)
    {
        log(LoggerLevel.ERROR,msg);
    }
    public void logFatal(String msg)
    {
        log(LoggerLevel.FATAL,msg);
    }
    public void logDebug(String msg)
    {
        log(LoggerLevel.DEBUG,msg);
    }


    public void setFlag(LoggerFlag flag)
    {
        this.flag=flag;
    }
}
