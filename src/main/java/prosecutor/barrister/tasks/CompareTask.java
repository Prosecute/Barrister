package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import prosecutor.barrister.Barrister;
import prosecutor.barrister.jaxb.*;
import prosecutor.barrister.jaxb.TrialConfiguration;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.report.logger.Logger;
import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.submissions.SubmissionsLocation;
import prosecutor.barrister.trial.Trial;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

@Logger(name = "Task-Compare")
public class CompareTask extends Task {

    private SubmissionManager submissionManager=new SubmissionManager();
    ConfigurationType configuration;
    private Trial[] trials;

    private ExecutorService executorService;

    public SubmissionManager getSubmissionManager()
    {
        return submissionManager;
    }

    public void setTrials(Trial[] trials) {
        this.trials = trials;
    }

    public void setConfiguration(ConfigurationType configuration)
    {
        ConfigurationType type=new ConfigurationType();
        type.setEntitiesLocations(configuration.getEntitiesLocations());
        type.setOptions(configuration.getOptions());
        type.setOutputEntityLocations(configuration.isOutputEntityLocations());
        type.setOutputLocation(configuration.getOutputLocation());
        type.setProjectName(configuration.getProjectName());
        type.setTrials(configuration.getTrials());

        this.configuration=type;
    }

    public Trial[] getTrials() {
        return trials;
    }

    @Override
    public String[] getTaskIDs() {
        return new String[]{"compare"};
    }

    @Override
    public String getTaskName() {
        return "Mode";
    }

    @Override
    public String getShortInfo() {
        return "compares source files for plagiarism.";
    }

    @Override
    public String[] getLongInfo() {
        return null;

    }
    public Report generateReport()
    {
        Date d=new Date();
        Report report=new Report();
        report.setConsoleOutput(new Report.ConsoleOutput());
        report.setConfiguration(configuration);
        report.setMatches(new Report.Matches());
        report.setErrors(new Report.Errors());
        report.setTool(Barrister.NAME);
        report.setVersion(Barrister.VERSION);
        report.setGenerated(XMLGregorianCalendarImpl.createDateTime(d.getYear(),d.getMonth(),d.getDay(),d.getHours(),d.getMinutes(),d.getSeconds()));
        L.setReport(report);
        L.logInfo("Preparing for testing");
        //Prepare threading
        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue();
        executorService=new ThreadPoolExecutor(Options.RUNTIME_THREAD_COUNT,Options.RUNTIME_THREAD_COUNT,0L, TimeUnit.MILLISECONDS,queue);
        for(EntitiesLocation loc:configuration.getEntitiesLocations().getEntitiesLocation()) {
            if(!Paths.get(loc.getPath()).toFile().exists())
            {
                L.logWarn("Location: "+loc.getName()+"("+loc.getPath()+") doesn´t exist. Excluding from testing.");
            }
            else
                submissionManager.addLocation(new SubmissionsLocation(Paths.get(loc.getPath()), loc.isDirect(), true, loc.getMode() == TestMode.TEST));
        }
        if(submissionManager.getSubmissionCount()==0)
        {
            L.logFatal("No submissions for testing. Shutting down.");
            return report;
        }
        if(configuration.getTrials().getTrial().isEmpty())
        {
            L.logFatal("No trials found. Shutting down.");
            return report;
        }

        List<TrialConfiguration> list=configuration.getTrials().getTrial();
        List<Integer> usedID=new ArrayList<>();
        for(TrialConfiguration conf:list)
            if(conf.getTrialID()!=null)
                if(usedID.contains(conf.getTrialID())) {
                    conf.setTrialID(null);
                    L.logWarn("Multiple same trial ID found. Trying to repair.");
                }
                else {
                    usedID.add(conf.getTrialID());
                }
        int c=0;
        for(TrialConfiguration conf:list)
        {
            if(conf.getTrialID()!=null)
                continue;
            while(usedID.contains(c))
                c++;
            conf.setTrialID(c);
            L.logInfo("Adding trial ID");
        }
        trials=new Trial[configuration.getTrials().getTrial().size()];
        //TODO add EntityLocations

        c=0;
        for(TrialConfiguration conf:configuration.getTrials().getTrial())
        {
            trials[c]=new Trial();
            trials[c].setLanguage(Language.resolve(conf.getTrialType().getName(), conf.getTrialType().getVersion()));
            c++;
        }
        L.logInfo("Executing trials");
        //Execute trials (synchronously)
        for(Trial trial:trials)
        {
            trial.execute(executorService,submissionManager);
        }
        L.logInfo("Execution complete");
        Date d2=new Date();
        report.setGenerateTime(XMLGregorianCalendarImpl.createDateTime(1, 1, 1, d2.getHours()-d.getHours(), d2.getMinutes()-d.getMinutes(), d2.getSeconds()-d.getSeconds()));
        return report;
    }

    @Override
    public void run() {

        generateReport();
        L.saveReport(Paths.get(configuration.getOutputLocation()));

    }

    @Override
    protected void resolveParams(String... params) {
        List<String> list=Arrays.asList(params);
        Iterator<String> iterator=list.iterator();
        while(iterator.hasNext())
        {
            String param=iterator.next();
            if(param.startsWith("-"))
            {
            }
            else
            {
                if(param.endsWith(".xml"))
                {
                    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    try {
                        JAXBContext jc = JAXBContext.newInstance(ConfigurationType.class);
                        Unmarshaller unmarshaller = jc.createUnmarshaller();
                        configuration = (ConfigurationType) unmarshaller.unmarshal(new File(param));
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
