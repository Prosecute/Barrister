package prosecutor.barrister.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Fry on 15.11.2016.
 */
public class TimeUtils {

    public static XMLGregorianCalendar getNow()
    {
        GregorianCalendar c=new GregorianCalendar();
        c.setTime(new Date());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;//TODO: HACK
        }
    }
    public static Date toDate(XMLGregorianCalendar calendar)
    {
        return calendar.toGregorianCalendar().getTime();
    }
    public static XMLGregorianCalendar toCalendar(Date date)
    {
        GregorianCalendar c=new GregorianCalendar();
        c.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null; //TODO: HACK
        }
    }
}
