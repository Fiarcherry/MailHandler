package app;

import mail.controllers.MessageHandler;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Работник - класс для чтения писем через консоль
 */
public class Worker {
    public static void main(String[] args) throws MessagingException, IOException, SQLException {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.readEmail("new");
    }
}
