package mail.controllers;

import common.Config;
import mail.models.EmailAuthenticator;

import javax.mail.*;
import java.util.Properties;

public class MailConnect {
    private static volatile MailConnect instance;

    private Session sessionIMAP;
    private Session sessionSMTP;

    private Store store;
    private Authenticator auth;


    public Store getStore(){
        return store;
    }

    public Session getSessionIMAP() {
        return sessionIMAP;
    }

    public Session getSessionSMTP() {
        return sessionSMTP;
    }


    public static MailConnect getInstance() throws MessagingException {
        MailConnect localInstnce = instance;
        if (localInstnce == null){
            synchronized (MailConnect.class){
                localInstnce = instance;
                if (localInstnce == null){
                    instance = localInstnce = new MailConnect();
                }
            }
        }
        return localInstnce;
    }

    private MailConnect() throws MessagingException {
        this.auth = new EmailAuthenticator(Config.getMailUsername(), Config.getMailPassword());
        ConnectIMAP();
        ConnectSMTP();
    }


    private void ConnectSMTP(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", Config.getMailHostSmtp());
        properties.setProperty("mail.smtp.port", Config.getMailPortSmtp());
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        sessionSMTP = Session.getInstance(properties, auth);
    }

    private void ConnectIMAP() throws MessagingException{
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
