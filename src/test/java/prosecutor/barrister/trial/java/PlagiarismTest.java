package prosecutor.barrister.trial.java;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.junit.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import prosecutor.barrister.Barrister;
import prosecutor.barrister.jaxb.ConfigurationType;
import prosecutor.barrister.jaxb.Report;
import prosecutor.barrister.languages.Tokens;
import prosecutor.barrister.report.logger.L;
import prosecutor.barrister.tasks.CompareTask;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import static org.testng.Assert.assertNotNull;
@Test(singleThreaded = true)
public class PlagiarismTest {

    ConfigurationType configuration=null;
    Report report;

    @BeforeTest
    public void generateTokens()
    {
        Tokens.generateIndex();
    }
    @AfterTest
    public void disposeTokens()
    {
        Tokens.resetIndexs();
    }

    @Test(groups = "init", description = "Tries to load configuration using Unmarshaller and JAXBContext.")
    public void configurationLoad() throws SAXException, MalformedURLException, JAXBException
    {
        String _filelocation="src/test/resources/language/java/inputConfPlagiarismTesting.xml";
        String _err0="Configuration loaded from location '"+_filelocation+"' is null.";
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        JAXBContext jc = JAXBContext.newInstance(ConfigurationType.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        configuration = (ConfigurationType) unmarshaller.unmarshal(new File(_filelocation));
        assertNotNull(configuration!=null,_err0);
    }

    @Test(groups = "integration", dependsOnGroups = "init.*")
    public void compareTest()
    {
        CompareTask task=new CompareTask();
        task.setConfiguration(configuration);
        report=task.getReport();
        assertNotNull(report);

    }

    @Test(dependsOnGroups = "init.*", groups = "integration", dependsOnMethods = "compareTest")
    public void resultIntegrityTesting()
    {
        L.setReport(report);

        L.saveReport(Paths.get("ttt.xml"));//TODO REMOVE
    }
}
