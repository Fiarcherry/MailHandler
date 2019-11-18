package mail.controllers;

import common.Config;
import mail.models.EmailAuthenticator;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

/**
 * Класс подключения к почтовому ящику
 */
public class MailConnect {
    private static volatile MailConnect instance;

    private Session sessionIMAP;
    private Session sessionSMTP;

    private Store store;
    private Authenticator auth;

    /**
     * Получение хранилища с письмами
     * @return хранилище с письмами
     */
    public Store getStore() {
        return store;
    }

    /**
     * Получение IMAP сессии
     * @returnIMAP сессия
     */
    public Session getSessionIMAP() {
        return sessionIMAP;
    }

    /**
     * Получение SMTP сессии
     * @returnSMTP сессия
     */
    public Session getSessionSMTP() {
        return sessionSMTP;
    }

    /**
     * Получение экземпляра класса
     * @return Экземпляр класса
     * @throws MessagingException
     */
    public static MailConnect getInstance() throws MessagingException {
        MailConnect localInstnce = instance;
        if (localInstnce == null) {
            synchronized (MailConnect.class) {
                localInstnce = instance;
                if (localInstnce == null) {
                    instance = localInstnce = new MailConnect();
                }
            }
        }
        return localInstnce;
    }

    /**
     * Подключение к почте
     * @throws MessagingException
     */
    private MailConnect() throws MessagingException {
        this.auth = new EmailAuthenticator(Config.getMailUsername(), Config.getMailPassword());
        ConnectIMAP();
        ConnectSMTP();
    }

    /**
     * Подключение к SMTP
     */
    private void ConnectSMTP() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", Config.getMailHostSmtp());
        properties.setProperty("mail.smtp.port", Config.getMailPortSmtp());
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        sessionSMTP = Session.getInstance(properties, auth);
    }

    /**
     * Подключение к IMAP
     * @throws MessagingException
     */
    private void ConnectIMAP() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.port", Config.getMailPortImap());

        sessionIMAP = Session.getInstance(properties, auth);

        store = sessionIMAP.getStore();
        store.connect(Config.getMailHostImap(), Config.getMailUsername(), Config.getMailPassword());
    }
}
