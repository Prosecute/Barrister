package prosecutor.barrister.tasks.options;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.filters.PathFilter;
import prosecutor.barrister.languages.Language;
import prosecutor.barrister.submissions.SubmissionManager;
import prosecutor.barrister.submissions.SubmissionsLocation;
import prosecutor.barrister.tasks.CompareTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import prosecutor.barrister.trial.Trial;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public abstract class OptionBuilderXML {

    protected static final String SCHEMA="http://d.cz/d.xsd";
    protected OptionBuilderXML()
    {}

    public static OptionBuilderXML fromFile(File file)
    {
        return new OptionBuilderFileXML(file);
    }

    public abstract boolean validate() throws Exception;

    public abstract void assign(CompareTask task) throws Exception;

    protected static class OptionBuilderFileXML extends OptionBuilderXML
    {
        private File file;

        public OptionBuilderFileXML(File file) {
        }

        @Override
        public boolean validate() throws Exception {
            Source xmlFile = new StreamSource(file);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new URL(SCHEMA));
            Validator validator = schema.newValidator();
            try {
                validator.validate(xmlFile);
                return true;
            } catch (SAXException e) {
                throw e;
            }
        }

        @Override
        public void assign(CompareTask task) throws Exception {

            //Preparing document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            //Filling submissions locations to task
            SubmissionManager manager=task.getSubmissionManager();
            NodeList nodeList = doc.getElementsByTagName("SubjectsLocation");
            for(int i=0;i<nodeList.getLength();i++)
            {
                Node node=nodeList.item(i);
                String path=node.getAttributes().getNamedItem("path").getNodeValue();
                if(!path.endsWith("/"))
                    path+="/";
                boolean direct=false;
                boolean compareOnly=false;
                boolean testOnly=false;
                if(node.getAttributes().getNamedItem("direct")!=null)
                    direct=Boolean.valueOf(node.getAttributes().getNamedItem("direct").getNodeValue());
                if(node.getAttributes().getNamedItem("mode")==null ||
                        node.getAttributes().getNamedItem("compareOnly").getNodeValue().equals("testAndCompare"))
                {}
                else if(node.getAttributes().getNamedItem("compareOnly").getNodeValue().equals("testOnly"))
                    testOnly=true;
                else
                    compareOnly=true;
                NodeList includes=((Element) node).getElementsByTagName("Include");
                NodeList excludes=((Element) node).getElementsByTagName("Exclude");
                Predicate<Path> filter=null,filter2=null;
                for(int includeIndex=0;includeIndex<includes.getLength();includeIndex++)
                {
                    String include="glob:"+includes.item(includeIndex).getNodeValue();
                    if(filter==null)
                        filter=new PathFilter(include);
                    else
                        filter=filter.and(new PathFilter(include));
                }
                for(int excludeIndex=0;excludeIndex<excludes.getLength();excludeIndex++)
                {
                    String exclude="glob:"+excludes.item(excludeIndex).getNodeValue();
                    if(filter2==null)
                        filter2=new PathFilter(exclude);
                    else
                        filter2=filter2.and(new PathFilter(exclude).negate());
                }

                SubmissionsLocation location=new SubmissionsLocation(Paths.get(path),direct,compareOnly,testOnly);
                if(filter!=null)
                    location.setFilter(filter.and(filter2));
                manager.addLocation(location);
            }

            //Creating trials
            nodeList = doc.getElementsByTagName("Trial");
            Trial[] trials=new Trial[nodeList.getLength()];
            for(int i=0;i<nodeList.getLength();i++)
            {
                Node node = nodeList.item(i);
                NodeList childs=node.getChildNodes();
                Trial trial=new Trial();

                //Setting language and used file extensions (if set)
                Element languageNode=(Element)((Element)node).getElementsByTagName("Language").item(0);
                Language language=Language.resolve(languageNode.getAttribute("name"), languageNode.hasAttribute("version")?languageNode.getAttribute("version"):null);
                NodeList extensions=null;
                for(int childPos=0;childPos<childs.getLength();childPos++)
                    if(childs.item(childPos).getNodeName().equals("Extensions"))
                        extensions=childs.item(childPos).getChildNodes();
                if(extensions!=null)
                {
                    String[] ex=new String[extensions.getLength()];
                    for(int extensionPos=0;extensionPos<extensions.getLength();extensionPos++)
                        ex[extensionPos]=extensions.item(extensionPos).getNodeValue();
                    language.setExtensions(ex);
                }
                trial.setLanguage(language);
                trials[i]=trial;
            }
            task.setTrials(trials);

            //Setting basecode


                //Setting output options


                //Setting test options

        }
    }
}
