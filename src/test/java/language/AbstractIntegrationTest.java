package language;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import prosecutor.barrister.jaxb.ConfigurationType;
import prosecutor.barrister.jaxb.Report;
import prosecutor.barrister.languages.Tokens;
import prosecutor.barrister.tasks.CompareTask;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.MalformedURLException;

import static org.testng.Assert.assertNotNull;

public abstract class AbstractIntegrationTest {

    protected ConfigurationType configuration=null;
    protected Report report;
    protected final String configurationLocation;

    public AbstractIntegrationTest(String configurationLocation)
    {
        this.configurationLocation=configurationLocation;
    }

    @BeforeTest
    public final void generateTokens()
    {
        Tokens.generateIndex();
    }
    @AfterTest
    public final void disposeTokens()
    {
        Tokens.resetIndexs();
    }


    @Test(groups = "init", description = "Tries to load configuration using Unmarshaller and JAXBContext.")
    public void configurationLoad() throws SAXException, MalformedURLException, JAXBException
    {
        String _err0="Configuration loaded from location '"+configurationLocation+"' is null.";
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        JAXBContext jc = JAXBContext.newInstance(ConfigurationType.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        configuration = (ConfigurationType) unmarshaller.unmarshal(new File(configurationLocation));
        assertNotNull(configuration!=null,_err0);
    }
    @Test(groups = "compare", dependsOnGroups = "init")
    public void compareTest()
    {
        CompareTask task=new CompareTask();
        task.setConfiguration(configuration);
        report=task.generateReport();
        assertNotNull(report);
    }
}
