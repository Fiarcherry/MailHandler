package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private final static String BUNDLE_NAME = "Params.properties";

    private static final String DB_TITLE = "db.title";

    private final static String HOST_IMAP= "mail.host_imap";
    private final static String HOST_SMTP= "mail.host_smpt";

    private final static String USERNAME = "mail.username";
    private final static String PASSWORD = "mail.password";

    private final static String PORT_IMAP = "mail.port_imap";
    private final static String PORT_STMP = "mail.port_smtp";

    private final static String LOG_FILE_NAME = "log.file_name";

    private static Properties config = new Properties();

    static{
        try{
            config.load(new FileInputStream(Paths.get("").toAbsolutePath().resolve(BUNDLE_NAME).toFile()));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String getDbFileName(){
        return config.getProperty(DB_TITLE);
    }

    public static String getMailHostImap(){
        return config.getProperty(HOST_IMAP);
    }
    public static String getMailHostSmtp(){
        return config.getProperty(HOST_SMTP);
    }
    public static String getMailPortImap(){
        return config.getProperty(PORT_IMAP);
    }
    public static String getMailPortSmtp(){
        return config.getProperty(PORT_STMP);
    }
    public static String getMailUsername(){
        return config.getProperty(USERNAME);
    }
    public static String getMailPassword(){
        return config.getProperty(PASSWORD);
    }
    public static String getLogFileName(){
        return config.getProperty(LOG_FILE_NAME);
    }
}
