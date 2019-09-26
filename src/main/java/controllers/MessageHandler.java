package controllers;

import models.EMessage;
import models.MailSessionIMAP;
import models.MailSessionSMTP;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;

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

    public void readEmail()
    {
        try {

            Folder inbox = MailSessionIMAP.getInstance().getStore().getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            System.out.println("Количество сообщений : " + inbox.getMessageCount());
            if (inbox.getMessageCount() == 0)
                return;

            for (int i = 1; i < 3; i++){
                MimeMessage message = (MimeMessage) inbox.getMessage(i);
                System.out.println("Тема сообщения: " + message.getSubject());
                System.out.println("Сообщение: " + message.getContent());
                System.out.println("Отправитель: " + Arrays.toString(message.getFrom()));
            }

        } catch (MessagingException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
