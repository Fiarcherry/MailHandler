package common;

import com.mpt.databasehandler.ConfigDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Config implements ConfigDB {

    private final static String BUNDLE_NAME = "Params.properties";

    private final static String DRIVER = "db.driver";

    private static final String DB_SQLITE_TITLE = "db.sqlite.title";

    private static final String DB_MYSQL_HOST = "db.mysql.host";
    private static final String DB_MYSQL_PORT = "db.mysql.port";
    private static final String DB_MYSQL_USESSL = "db.mysql.useSSL";
    private static final String DB_MYSQL_USE_UNICODE = "db.mysql.useUnicode";
    private static final String DB_MYSQL_USE_LEGACY_DATETIME_CODE= "db.mysql.useLegacyDatetimeCode";
    private static final String DB_MYSQL_REQUIRE_SSL= "db.mysql.requireSSL";
    private static final String DB_MYSQL_VERIFY_SERVER_CERTIFICATE= "db.mysql.verifyServerCertificate";
    private static final String DB_MYSQL_SERVERTIMEZONE = "db.mysql.serverTimeZone";
    private static final String DB_MYSQL_DBNAME = "db.mysql.dbname";
    private static final String DB_MYSQL_USER_NAME = "db.mysql.user.name";
    private static final String DB_MYSQL_USER_PASSWORD = "db.mysql.user.password";


    private final static String HOST_IMAP= "mail.host_imap";
    private final static String HOST_SMTP= "mail.host_smpt";

    private final static String USERNAME = "mail.username";
    private final static String PASSWORD = "mail.password";

    private final static String PORT_IMAP = "mail.port_imap";
    private final static String PORT_STMP = "mail.port_smtp";

    private final static String LOG_ISENABLE = "isEnable";
    private final static String LOG_FILE_NAME = "log.file_name";

    public final static Config config = new Config();

    private static Properties properties = new Properties();

    static{
        try{
            properties.load(new FileInputStream(Paths.get("").toAbsolutePath().resolve(BUNDLE_NAME).toFile()));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //region DataBase config
    @Override
    public String get_DB_Driver() {
        return properties.getProperty(DRIVER);
    }

    @Override
    public String get_DB_SQLite_FileName() {
        return properties.getProperty(DB_SQLITE_TITLE);
    }

    @Override
    public String get_DB_MySQL_Host() {
        return properties.getProperty(DB_MYSQL_HOST);
    }
    @Override
    public String get_DB_MySQL_Port() {
        return properties.getProperty(DB_MYSQL_PORT);
    }
    @Override
    public String get_DB_MySQL_UseSSL() {
        return properties.getProperty(DB_MYSQL_USESSL);
    }
    @Override
    public String get_DB_MySQL_UseUnicode() {
        return properties.getProperty(DB_MYSQL_USE_UNICODE);
    }
    @Override
    public String get_DB_MySQL_UseLegacyDatetimeCode() {
        return properties.getProperty(DB_MYSQL_USE_LEGACY_DATETIME_CODE);
    }
    @Override
    public String get_DB_MySQL_RequireSSL() {
        return properties.getProperty(DB_MYSQL_REQUIRE_SSL);
    }
    @Override
    public String get_DB_MySQL_VerifyServerCertificate() {
        return properties.getProperty(DB_MYSQL_VERIFY_SERVER_CERTIFICATE);
    }
    @Override
    public String get_DB_MySQL_ServerTimeZone() {
        return properties.getProperty(DB_MYSQL_SERVERTIMEZONE);
    }
    @Override
    public String get_DB_MySQL_DataBaseTitle() {
        return properties.getProperty(DB_MYSQL_DBNAME);
    }
    @Override
    public String get_DB_MySQL_User_Name() {
        return properties.getProperty(DB_MYSQL_USER_NAME);
    }
    @Override
    public String get_DB_MySQL_User_Password() {
        return properties.getProperty(DB_MYSQL_USER_PASSWORD);
    }
    //endregion

    //region Mail config
    public static String getMailHostImap(){
        return properties.getProperty(HOST_IMAP);
    }
    public static String getMailHostSmtp(){
        return properties.getProperty(HOST_SMTP);
    }
    public static String getMailPortImap(){
        return properties.getProperty(PORT_IMAP);
    }
    public static String getMailPortSmtp(){
        return properties.getProperty(PORT_STMP);
    }
    public static String getMailUsername(){
        return properties.getProperty(USERNAME);
    }
    public static String getMailPassword(){
        return properties.getProperty(PASSWORD);
    }
    public static String getLogFileName(){
        return properties.getProperty(LOG_FILE_NAME);
    }
    //endregion
}
