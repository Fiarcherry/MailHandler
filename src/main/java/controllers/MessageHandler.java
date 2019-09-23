package controllers;

import models.EMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
}
