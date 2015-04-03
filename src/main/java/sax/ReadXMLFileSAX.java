package sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Виталий on 03.04.2015.
 */
public class ReadXMLFileSAX {
    public static Object readXML(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFile, handler);

            return handler.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}