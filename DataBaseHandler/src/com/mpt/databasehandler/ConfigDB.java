package com.mpt.databasehandler;

/**
 * Интерфейс конфигурации базы данных
 */
public interface ConfigDB {

    public String get_DB_Driver();

    public String get_DB_SQLite_FileName();

    public String get_DB_MySQL_Host();
    public String get_DB_MySQL_Port();
    public String get_DB_MySQL_UseSSL();
    public String get_DB_MySQL_UseUnicode();
    public String get_DB_MySQL_UseLegacyDatetimeCode();
    public String get_DB_MySQL_RequireSSL();
    public String get_DB_MySQL_VerifyServerCertificate();
    public String get_DB_MySQL_ServerTimeZone();
    public String get_DB_MySQL_DataBaseTitle();
    public String get_DB_MySQL_User_Name();
    public String get_DB_MySQL_User_Password();
}
