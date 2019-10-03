package Mail.Controllers;

import DataBase.Models.PaymentM;
import Mail.Models.EMessage;

import javax.jnlp.DownloadService;
import javax.jnlp.DownloadServiceListener;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.sun.xml.internal.ws.message.MimeAttachmentSet;
import org.w3c.dom.*;

import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.*;
import javax.xml.soap.Node;
import java.io.*;
import java.util.LinkedList;

public class MessageHandler {



    public String sendMessage(EMessage messageData){

        try {
            MimeMessage message = new MimeMessage(MailConnect.getInstance().getSessionSMTP());
            message.setText(messageData.getMessageText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageData.getTo()));
            message.setSubject(messageData.getSubject());

            Transport.send(message);
            return "Success.";
        }
        catch (MessagingException e){
            return e.getMessage();
        }
    }

    public void sendMessages(EMessage[] messages) throws MessagingException{
        Session session = MailConnect.getInstance().getSessionSMTP();
        for (EMessage eMessage : messages){
            MimeMessage message = new MimeMessage(session);
            message.setSubject(eMessage.getSubject());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(eMessage.getTo()));
            message.setText(eMessage.getMessageText());

            Transport.send(message);
        }
    }

    public void sendPayments(PaymentM[] payments) throws MessagingException{
        EMessage[] messages = new EMessage[payments.length];
        for (int i = 0; i < payments.length; i++){
            StringBuilder text = new StringBuilder();
            text.append("Сообщаем вам о удачном платеже.")
                    .append("\nНомер аккаунта: ").append(payments[i].getAccount())
                    .append("\nДата проведения операции: ").append(payments[i].getDateOperation())
                    .append("\nСумма платежа: ").append(payments[i].getAmount())
                    .append("\nВ том числе комиссия: ").append(payments[i].getCommission());
            messages[i] = new EMessage(payments[i].getEmail(), "Payments", text.toString());
        }
        sendMessages(messages);
    }

    public void readEmail() throws MessagingException, IOException {
        Folder inbox = MailConnect.getInstance().getStore().getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        System.out.println("Количество сообщений : " + inbox.getMessageCount());
        if (inbox.getMessageCount() == 0)
            return;

        for (int i = 1; i < 2; i++){
            //MimeMessage message = (MimeMessage) inbox.getMessage(i);
            //System.out.println("Тема сообщения: " + message.getSubject());
            //System.out.println("Сообщение: " + message.getContent());
            //System.out.println("Отправитель: " + Arrays.toString(message.getFrom()));

            Message message = inbox.getMessage(inbox.getMessageCount());
            String attachment = "";
            Multipart mp = (Multipart) message.getContent();

            for (int j = 0; j < mp.getCount(); j++){
                Part part = mp.getBodyPart(j);
//                saveFile("90926.10898", part.getInputStream());

                String disp = part.getDisposition();
                if (disp != null) {
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (!mbp.isMimeType("text/plain")) {
                        File savedir = new File("/home/student/");
                        savedir.mkdirs();
                        File savefile = File.createTempFile("emailattach", ".atch", savedir );
                        try {
                            int path = saveFile(savefile, part);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //findPayments();
            }
        }

        inbox.close();
    }

    public void findPayments(){

        try {
            File inputFile = new File("90926.10898");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<Payment Client=\"Cherry\">\n" +
                    "    <Uni>r6lpoptgkeki9l14zu24hiapw</Uni>\n" +
                    "    <Number>1197145776</Number>\n" +
                    "    <DateOperation>2019-09-25T00:18:59</DateOperation>\n" +
                    "    <Account>118469534609</Account>\n" +
                    "    <Amount>70478.14</Amount>\n" +
                    "    <Commission>204.39</Commission>\n" +
                    "</Payment>");
            ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(inputFile);

            Element root = doc.getDocumentElement();

            System.out.println("---------------");
            System.out.println("Client: " + root.getAttribute("Client"));
            System.out.println("Uni: " + root.getElementsByTagName("Uni").item(0).getTextContent());
            System.out.println("Number: " + root.getElementsByTagName("Number").item(0).getTextContent());
            System.out.println("DateOperation: " + root.getElementsByTagName("DateOperation").item(0).getTextContent());
            System.out.println("Account: " + root.getElementsByTagName("Account").item(0).getTextContent());
            System.out.println("Amount: " + root.getElementsByTagName("Amount").item(0).getTextContent());
            System.out.println("Commission: " + root.getElementsByTagName("Commission").item(0).getTextContent());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

protected int saveFile(File saveFile, Part part) throws Exception {

    BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(saveFile) );

    byte[] buff = new byte[2048];
    InputStream is = part.getInputStream();
    int ret = 0, count = 0;
    while( (ret = is.read(buff)) > 0 ){
        bos.write(buff, 0, ret);
        count += ret;
    }
    bos.close();
    is.close();
    return count;
}
}
