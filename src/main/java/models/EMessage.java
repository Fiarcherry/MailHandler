package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMessage {

    private String to;
    private final static String HOST = "smtp.gmail.com";
    private final static String username = "1spangls1@gmail.com";
    private String password;

    public EMessage(String to, String password) {
        this.to = to;
        this.password = password;
    }

    public void sendMessage(String messageText){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setText(messageText);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(username);
            message.setSubject("Test theme");

            Transport.send(message);
        }
        catch (MessagingException e){
            System.out.println(e.getMessage());
        }
    }
}
