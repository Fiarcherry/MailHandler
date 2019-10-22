package database.controllers;

import database.models.PaymentM;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {

    public static PaymentM parsePayment(File savefile){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "MessageTemp" + System.getProperty("file.separator") +savefile.getName());
            Document doc = builder.parse(xmlFile);

            Element root = doc.getDocumentElement();

            return new PaymentM(
                    root.getElementsByTagName("Uni").item(0).getTextContent(),
                    root.getElementsByTagName("Number").item(0).getTextContent(),
                    root.getElementsByTagName("DateOperation").item(0).getTextContent(),
                    Float.valueOf(root.getElementsByTagName("Amount").item(0).getTextContent()),
                    Float.valueOf(root.getElementsByTagName("Commission").item(0).getTextContent()),
                    false);
        }
        catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e){
            e.printStackTrace();
        }
        return null;
    }
}
