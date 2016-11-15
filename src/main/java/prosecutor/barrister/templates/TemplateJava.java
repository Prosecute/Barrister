package prosecutor.barrister.templates;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.ConfigurationType;
import prosecutor.barrister.jaxb.TrialCategory;
import prosecutor.barrister.jaxb.TrialConfiguration;

public class TemplateJava {


    public ConfigurationType getDefaultJavaConfiguration()
    {
        ConfigurationType type=new ConfigurationType();
        type.setOutputLocation("Java_Report.brr");
        type.setProjectName("Java plagiarism detector");

        ConfigurationType.Trials trials=new ConfigurationType.Trials();

        TrialConfiguration trialConfiguration =new TrialConfiguration();
        TrialConfiguration.TrialType trialType=new TrialConfiguration.TrialType();
        trialType.setCategory(TrialCategory.SOURCECODE);
        trialType.setMode("TokenMatch");
        trialType.setName("Java");
        trialType.setVersion("1.8");
        trialConfiguration.setTrialType(trialType);
        trialConfiguration.setTrialName("Source code trial - Java (Token match)");

        trials.getTrial().add(trialConfiguration);
        type.setTrials(trials);


        return type;
    }
}
