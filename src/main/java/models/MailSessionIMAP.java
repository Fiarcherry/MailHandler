package models;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class MailSessionIMAP {

    private final static String HOST_IMAP= "imap.gmail.com";
    private final static String USERNAME = "1spangls1";
    private final static String PASSWORD = "uerjbsflkdrqkgcu";
    private final static String PORT_IMAP = "465";

    private static volatile MailSessionIMAP instance;

    private Session sessionIMAP;

    /**
     * Получение сессии для просмотра списка полученых писем
     * @return Возвращает сессию для просмотра списка полученных писем
     */
    public Session getSession(){
        return sessionIMAP;
    }

    public static MailSessionIMAP getInstance() {
        MailSessionIMAP localInstnce = instance;
        if (localInstnce == null){
            synchronized (MailSessionIMAP.class){
                localInstnce = instance;
                if (localInstnce == null){
                    instance = localInstnce = new MailSessionIMAP();
                }
            }
        }
        return localInstnce;
    }

    private MailSessionIMAP(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", HOST_IMAP);
        //properties.setProperty("mail.smtp.port", PORT);
        properties.setProperty("mail.imap.ssl.enable", "true");
        //properties.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new EmailAuthenticator(USERNAME, PASSWORD);

        sessionIMAP = Session.getInstance(properties, auth);
    }
}
