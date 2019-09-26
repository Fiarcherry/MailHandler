package controllers;

import models.EMessage;
import models.EmailAuthenticator;
import models.MailSessionSMTP;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MessageHandler {



    public String sendMessage(EMessage messageData){

        try {
            MimeMessage message = new MimeMessage(MailSessionSMTP.getSessionSMTP().getSession());
            message.setText(messageData.getMessageText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageData.getTo()));
            message.setSubject("Test theme");

            Transport.send(message);
            return "Success.";
        }
        catch (MessagingException e){
            return e.getMessage();
        }
    }
}
