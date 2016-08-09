package prosecutor.barrister.tasks;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.EntitiesLocation;
import prosecutor.barrister.jaxb.TestMode;
import prosecutor.barrister.jaxb.TrialConfiguration;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.submissions.SubmissionsLocation;
import prosecutor.barrister.trial.Trial;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class CompareTask extends Task {

    private SubmissionManager submissionManager=new SubmissionManager();
    Configuration configuration;
    private Trial[] trials;

    private ExecutorService executorService;

    public SubmissionManager getSubmissionManager()
    {
        return submissionManager;
    }

    public void setTrials(Trial[] trials) {
        this.trials = trials;
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
        return "Compare";
    }

    @Override
    public String getShortInfo() {
        return null;
    }

    @Override
    public String getLongInfo() {
        return null;
    }

    @Override
    public void run() {
        //Prepare threading
        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue();
        executorService=new ThreadPoolExecutor(Options.RUNTIME_THREAD_COUNT,Options.RUNTIME_THREAD_COUNT,0L, TimeUnit.MILLISECONDS,queue);
        for(EntitiesLocation loc:configuration.getEntitiesLocations().getEntitiesLocation())
            submissionManager.addLocation(new SubmissionsLocation(Paths.get(loc.getPath()),loc.isDirect(),true,loc.getMode()==TestMode.TEST));
        trials=new Trial[configuration.getTrials().getTrial().size()];
        int c=0;
        for(TrialConfiguration conf:configuration.getTrials().getTrial())
        {
            trials[c]=new Trial();
            trials[c].setLanguage(Language.resolve(conf.getLanguage().getName(),conf.getLanguage().getVersion()));
            c++;
        }
        //Execute trials (synchronously)
        for(Trial trial:trials)
        {
            trial.execute(executorService,submissionManager);
            //trial.execute(executorService,queue);
        }

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
                //TODO
            }
            else
            {
                if(param.endsWith(".xml"))
                {
                    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    try {
                        JAXBContext jc = JAXBContext.newInstance(Configuration.class);
                        Unmarshaller unmarshaller = jc.createUnmarshaller();
                        configuration = (Configuration) unmarshaller.unmarshal(new File(param));
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                    Configuration configuration=null; //TODO
                }
                //TODO
            }
        }
    }
}
