package models;

import javax.mail.*;
import java.util.Properties;

public class MailSessionIMAP {

    private final static String HOST_IMAP= "imap.gmail.com";
    private final static String USERNAME = "1spangls1";
    private final static String PASSWORD = "uerjbsflkdrqkgcu";
    private final static String PORT_IMAP = "993";

    private static volatile MailSessionIMAP instance;

    private Session sessionIMAP;
    private Store store;

    /**
     * Получение сессии для просмотра списка полученых писем
     * @return Возвращает сессию для просмотра списка полученных писем
     */
    public Store getStore(){
        return store;
    }

    public static MailSessionIMAP getInstance() throws MessagingException {
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

    private MailSessionIMAP() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.port", PORT_IMAP);

        Authenticator auth = new EmailAuthenticator(USERNAME, PASSWORD);

        sessionIMAP = Session.getInstance(properties, auth);

        store = sessionIMAP.getStore();
        store.connect(HOST_IMAP, USERNAME, PASSWORD);
    }
}
