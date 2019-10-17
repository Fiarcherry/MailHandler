package app;

import mail.controllers.MessageHandler;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

public class Worker {
    public static void main(String[] args) throws MessagingException, IOException, SQLException {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.readEmail();
    }
}
