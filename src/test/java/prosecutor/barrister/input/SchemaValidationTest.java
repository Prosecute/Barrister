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
import org.xml.sax.SAXException;
import prosecutor.barrister.jaxb.Configuration;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SchemaValidationTest {

    @Test
    public void validateTest() throws SAXException, MalformedURLException, JAXBException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        JAXBContext jc = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Configuration customer = (Configuration) unmarshaller.unmarshal(new File("src/test/resources/inputConfigurations/inputConf0.xml"));
    }
}
