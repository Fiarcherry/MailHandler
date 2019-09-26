package controllers;

import models.EMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MessageHandler {

    private final static String HOST = "smtp.gmail.com";
    private final static String username = "1spangls1@gmail.com";

    public void sendMessage(EMessage messageData){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.ssl.enable", "true");

        final String password = messageData.getPassword();

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setText(messageData.getMessageText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageData.getTo()));
            message.setFrom(username);
            message.setSubject("Test theme");

            Transport.send(message);
        }
        catch (MessagingException e){
            System.out.println(e.getMessage());
        }
    }

    public void readEmail()
    {
        String IMAP_AUTH_EMAIL = "1spangls1@gmail.com";
        String IMAP_AUTH_PWD = "uerjbsflkdrqkgcu";
        String IMAP_Server = "imap.gmail.com";
        String IMAP_Port = "993";

        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAP_Port);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, "uerjbsflkdrqkgcu");
            }
        });

        try {
            Store store = session.getStore();
            store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            System.out.println("Количество сообщений : " + inbox.getMessageCount());
            if (inbox.getMessageCount() == 0)
                return;

            for (int i = 1; i < 11; i++){
                Message message = inbox.getMessage(i);
                System.out.println(message.getContent());
            }

        } catch (MessagingException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
