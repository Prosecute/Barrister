package prosecutor.barrister.templates;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.jaxb.*;

public class TemplateJava {


    public ConfigurationType getDefaultJavaConfiguration()
    {
        ConfigurationType type=new ConfigurationType();
        type.setOutputLocation("Java_Report.brr");
        type.setProjectName("Java plagiarism detector");

        ConfigurationType.Trials trials=new ConfigurationType.Trials();

        TrialConfiguration trialConfiguration =new TrialConfiguration();
        TrialConfiguration.TrialType trialType=new TrialConfiguration.TrialType();
        trialType.setSourceCode(new SourceCodeType());
        trialType.getSourceCode().setMode(new SourceCodeType.Mode());
        trialType.getSourceCode().getMode().setTokenCompareMode(new TokenCompareMode());
        trialType.getSourceCode().getMode().getTokenCompareMode().setAlgorithm("GSTiling");
        trialType.getSourceCode().setName("Java");
        trialType.getSourceCode().setVersion("1.8");
        trialConfiguration.setTrialType(trialType);
        trialConfiguration.setTrialName("Source code trial - Java (Token match)");

        trials.getTrial().add(trialConfiguration);
        type.setTrials(trials);


        return type;
    }
}
