package database.controllers;

import database.models.PaymentM;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Обработчик XML-файлов
 */
public class XMLParser {

    /**
     * Обработка сохранённого файла
     *
     * @param savefile Сохранённый файл
     * @return платёж на добавление в базу данных или ничего
     */
    public static PaymentM parsePayment(File savefile) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "MessageTemp" + System.getProperty("file.separator") + savefile.getName());
            Document doc = builder.parse(xmlFile);

            Element root = doc.getDocumentElement();

            return new PaymentM(
                    root.getElementsByTagName("Id").item(0).getTextContent(),
                    root.getElementsByTagName("IdOrder").item(0).getTextContent(),
                    root.getElementsByTagName("DatePayment").item(0).getTextContent(),
                    Float.valueOf(root.getElementsByTagName("AmountPayment").item(0).getTextContent()),
                    Float.valueOf(root.getElementsByTagName("CommissionPayment").item(0).getTextContent()),
                    false);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
