package prosecutor.barrister.input;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.xml.sax.SAXException;
import prosecutor.barrister.jaxb.Configuration;
import prosecutor.barrister.jaxb.TrialConfiguration;
import prosecutor.barrister.trial.Trial;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class InputFileValidationTest {

    Configuration configuration=null;
    @Test(groups = "init", description = "Tries to load configuration using Unmarshaller and JAXBContext.")
    public void configurationLoaded() throws SAXException, MalformedURLException, JAXBException
    {
        String _filelocation="src/test/resources/inputConfigurations/inputConf0.xml";
        String _err0="Configuration loaded from location '"+_filelocation+"' is null.";
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        JAXBContext jc = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        configuration = (Configuration) unmarshaller.unmarshal(new File(_filelocation));
        assertNotNull(configuration!=null,_err0);
    }

    @Test(dependsOnGroups = "init.*", groups = "unit", description = "")
    public void validateEntitiesLocation(){
        String _err0="EntitiesLocations doesn´t exist or is empty.";
        String _err1="EntitiesLocations exist, but EntitiesLocation is null or path doesn´t match.";
        assertTrue(configuration.getEntitiesLocations() != null,_err0);
        assertTrue(configuration.getEntitiesLocations().getEntitiesLocation() != null,_err0);
        assertTrue(configuration.getEntitiesLocations().getEntitiesLocation().size()!=1,_err0);
        assertTrue(configuration.getEntitiesLocations().getEntitiesLocation().get(0)!=null,_err1);
        assertTrue(configuration.getEntitiesLocations().getEntitiesLocation().get(0).getPath().equals("src/test/resources/language/java/submissionsRepository"),_err1);
    }

    @Test(dependsOnGroups = "init.*", groups = "unit")
    public void validateTrials()
    {
        String _err0="Trials doesn´t exist or is empty.";
        String _err1="Trials exist, but Trial is null or params doesn´t match.";
        assertTrue(configuration.getTrials()!=null,_err0);
        assertTrue(configuration.getTrials().getTrial()!=null,_err0);
        assertTrue(configuration.getTrials().getTrial().size()!=1,_err0);
        assertTrue(configuration.getTrials().getTrial().get(0)!=null,_err1);
        TrialConfiguration trial=configuration.getTrials().getTrial().get(0);
        assertTrue(trial.getLanguage().getName().equals("java"),_err1);
        assertTrue(trial.getLanguage().getVersion().equals("1.8"),_err1);
        assertTrue(trial.getTrialName().equals("FirstTrial"),_err1);

    }
}
