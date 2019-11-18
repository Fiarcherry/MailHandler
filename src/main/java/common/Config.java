package common;

import com.mpt.databasehandler.ConfigDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Класс конфигурации
 */
public class Config implements ConfigDB {

    //Название пакета с конфигурацией
    private final static String BUNDLE_NAME = "Params.properties";

    private final static String DRIVER = "db.driver";

    private static final String DB_SQLITE_TITLE = "db.sqlite.title";

    private static final String DB_MYSQL_HOST = "db.mysql.host";
    private static final String DB_MYSQL_PORT = "db.mysql.port";
    private static final String DB_MYSQL_USESSL = "db.mysql.useSSL";
    private static final String DB_MYSQL_USE_UNICODE = "db.mysql.useUnicode";
    private static final String DB_MYSQL_USE_LEGACY_DATETIME_CODE = "db.mysql.useLegacyDatetimeCode";
    private static final String DB_MYSQL_REQUIRE_SSL = "db.mysql.requireSSL";
    private static final String DB_MYSQL_VERIFY_SERVER_CERTIFICATE = "db.mysql.verifyServerCertificate";
    private static final String DB_MYSQL_SERVERTIMEZONE = "db.mysql.serverTimeZone";
    private static final String DB_MYSQL_DBNAME = "db.mysql.dbname";
    private static final String DB_MYSQL_USER_NAME = "db.mysql.user.name";
    private static final String DB_MYSQL_USER_PASSWORD = "db.mysql.user.password";

    private final static String HOST_IMAP = "mail.host_imap";
    //Хост протокола SMTP
    private final static String HOST_SMTP = "mail.host_smpt";

    //Логин к почте
    private final static String USERNAME = "mail.username";
    //Пароль к почте
    private final static String PASSWORD = "mail.password";

    //Порт протокола IMAP
    private final static String PORT_IMAP = "mail.port_imap";
    //Порт протокола SMTP
    private final static String PORT_STMP = "mail.port_smtp";

    //Включение log
    private final static String LOG_ISENABLE = "isEnable";
    //Название log-файла
    private final static String LOG_FILE_NAME = "log.file_name";

    public final static Config config = new Config();

    private static Properties properties = new Properties();

    /**
     * Загрузка конфигурации из файла
     */
    static {
        try {
            properties.load(new FileInputStream(Paths.get("").toAbsolutePath().resolve(BUNDLE_NAME).toFile()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //region DataBase config

    /**
     * Чтение параметра вида базы данных
     * @return Вид базы данных
     */
    @Override
    public String get_DB_Driver() {
        return properties.getProperty(DRIVER);
    }

    /**
     * Чтение параметра заголовка базы данных
     * @return Заголовок базы данных
     */
    @Override
    public String get_DB_SQLite_FileName() {
        return properties.getProperty(DB_SQLITE_TITLE);
    }

    /**
     * Чтение параметра хоста
     * @return Хост
     */
    @Override
    public String get_DB_MySQL_Host() {
        return properties.getProperty(DB_MYSQL_HOST);
    }

    /**
     * Чтение параметра порта
     * @return Порт
     */
    @Override
    public String get_DB_MySQL_Port() {
        return properties.getProperty(DB_MYSQL_PORT);
    }

    /**
     * Чтение параметра использования SSL
     * @return Использование SSL
     */
    @Override
    public String get_DB_MySQL_UseSSL() {
        return properties.getProperty(DB_MYSQL_USESSL);
    }

    /**
     * Чтение параметра использования Unicode
     * @return Использование Unicode
     */
    @Override
    public String get_DB_MySQL_UseUnicode() {
        return properties.getProperty(DB_MYSQL_USE_UNICODE);
    }

    /**
     * Чтение параметра использования кода унаследованных даты и времени
     * @return Использование кода унаследованных даты и времени
     */
    @Override
    public String get_DB_MySQL_UseLegacyDatetimeCode() {
        return properties.getProperty(DB_MYSQL_USE_LEGACY_DATETIME_CODE);
    }

    /**
     * Чтение параметра необходимости SSL
     * @return Необходимость SSL
     */
    @Override
    public String get_DB_MySQL_RequireSSL() {
        return properties.getProperty(DB_MYSQL_REQUIRE_SSL);
    }

    /**
     * Чтение параметра подтверждение сертификата сервера
     * @return Подтверждение сертификата сервера
     */
    @Override
    public String get_DB_MySQL_VerifyServerCertificate() {
        return properties.getProperty(DB_MYSQL_VERIFY_SERVER_CERTIFICATE);
    }

    /**
     * Чтение параметра временного пояса сервера
     * @return Временной пояс сервера
     */
    @Override
    public String get_DB_MySQL_ServerTimeZone() {
        return properties.getProperty(DB_MYSQL_SERVERTIMEZONE);
    }

    /**
     * Чтение параметра названия базы данных
     * @return Название базы данных
     */
    @Override
    public String get_DB_MySQL_DataBaseTitle() {
        return properties.getProperty(DB_MYSQL_DBNAME);
    }

    /**
     * Чтение параметра логина к базе данных
     * @return Логин к базе данных
     */
    @Override
    public String get_DB_MySQL_User_Name() {
        return properties.getProperty(DB_MYSQL_USER_NAME);
    }

    /**
     * Чтение параметра пароля к базе данных
     * @return Пароль к базе данных
     */
    @Override
    public String get_DB_MySQL_User_Password() {
        return properties.getProperty(DB_MYSQL_USER_PASSWORD);
    }
    //endregion

    //region Mail config

    /**
     * Чтение параметра хоста протокола IMAP
     * @return Хост протокола IMAP
     */
    public static String getMailHostImap() {
        return properties.getProperty(HOST_IMAP);
    }

    /**
     * Чтение параметра хоста протокола SMTP
     * @return Хост протокола SMTP
     */
    public static String getMailHostSmtp() {
        return properties.getProperty(HOST_SMTP);
    }

    /**
     * Чтение параметра порта протокола IMAP
     * @return Порт протокола IMAP
     */
    public static String getMailPortImap() {
        return properties.getProperty(PORT_IMAP);
    }

    /**
     *Чтение параметра порта протокола SMPT
     * @return Порт протокола SMTP
     */
    public static String getMailPortSmtp() {
        return properties.getProperty(PORT_STMP);
    }

    /**
     *Чтение параметра логина к почте
     * @return Логин к почте
     */
    public static String getMailUsername() {
        return properties.getProperty(USERNAME);
    }

    /**
     *Чтение параметра пароля к почте
     * @return Пароль к почте
     */
    public static String getMailPassword() {
        return properties.getProperty(PASSWORD);
    }

    /**
     *Чтение параметра названия log-файла
     * @return Название log-файла
     */
    public static String getLogFileName() {
        return properties.getProperty(LOG_FILE_NAME);
    }
    //endregion
}
