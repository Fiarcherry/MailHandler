package models;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class MailSessionSMTP {

    private final static String HOST_SMTP= "smtp.gmail.com";
    private final static String USERNAME = "1spangls1";
    private final static String PASSWORD = "uerjbsflkdrqkgcu";
    private final static String PORT_STMP = "465";

    private static volatile MailSessionSMTP instance;

    private Session sessionSMTP;

    /**
     * Получение сессии для отправки писем
     * @return Возвращает сессию для отправки писем
     */
    public Session getSession(){
        return sessionSMTP;
    }

    public static MailSessionSMTP getSessionSMTP() {
        MailSessionSMTP localInstnce = instance;
        if (localInstnce == null){
            synchronized (MailSessionSMTP.class){
                localInstnce = instance;
                if (localInstnce == null){
                    instance = localInstnce = new MailSessionSMTP();
                }
            }
        }
        return localInstnce;
    }

    private MailSessionSMTP() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", HOST_SMTP);
        properties.setProperty("mail.smtp.port", PORT_STMP);
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new EmailAuthenticator(USERNAME, PASSWORD);

        sessionSMTP = Session.getInstance(properties, auth);
    }



}
