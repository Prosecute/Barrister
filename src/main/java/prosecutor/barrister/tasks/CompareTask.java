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
        this.configuration=configuration;
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
    public Report getReport()
    {
        Date d=new Date();
        //Prepare threading
        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue();
        executorService=new ThreadPoolExecutor(Options.RUNTIME_THREAD_COUNT,Options.RUNTIME_THREAD_COUNT,0L, TimeUnit.MILLISECONDS,queue);
        for(EntitiesLocation loc:configuration.getEntitiesLocations().getEntitiesLocation())
            submissionManager.addLocation(new SubmissionsLocation(Paths.get(loc.getPath()),loc.isDirect(),true,loc.getMode()==TestMode.TEST));

        List<TrialConfiguration> list=configuration.getTrials().getTrial();
        List<Integer> usedID=new ArrayList<>();
        for(TrialConfiguration conf:list)
            if(conf.getTrialID()!=null)
                if(usedID.contains(conf.getTrialID()))
                    conf.setTrialID(null);
                else
                    usedID.add(conf.getTrialID());
        int c=0;
        for(TrialConfiguration conf:list)
        {
            if(conf.getTrialID()!=null)
                continue;
            while(usedID.contains(c))
                c++;
            conf.setTrialID(c);
        }

        trials=new Trial[configuration.getTrials().getTrial().size()];
        Report report=new Report();
        report.setConfiguration(configuration);
        report.setMatches(new Report.Matches());
        report.setErrors(new Report.Errors());
        report.setTool(Barrister.NAME);
        report.setVersion(Barrister.VERSION);
        report.setGenerated(XMLGregorianCalendarImpl.createDateTime(d.getYear(),d.getMonth(),d.getDay(),d.getHours(),d.getMinutes(),d.getSeconds()));
        //TODO add EntityLocations
        L.setReport(report);

        c=0;
        for(TrialConfiguration conf:configuration.getTrials().getTrial())
        {
            trials[c]=new Trial();
            trials[c].setLanguage(Language.resolve(conf.getTrialType().getName(), conf.getTrialType().getVersion()));
            c++;
        }
        //Execute trials (synchronously)
        for(Trial trial:trials)
        {
            trial.execute(executorService,submissionManager);
        }
        Date d2=new Date();
        //report.setGenerateTime(XMLGregorianCalendarImpl.createDateTime(1, 1, 1, d2.getHours()-d.getHours(), d2.getMinutes()-d.getMinutes(), d2.getSeconds()-d.getSeconds()));
        return report;
    }

    @Override
    public void run() {

        getReport();
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
