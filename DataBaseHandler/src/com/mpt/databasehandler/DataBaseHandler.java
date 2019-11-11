package com.mpt.databasehandler;

import org.sqlite.SQLiteDataSource;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class DataBaseHandler {
    private static DataBaseHandler instance;

    private Connection connection;
    private ConfigDB config;

    public static DataBaseHandler getInstance() {
        DataBaseHandler localInstance = instance;
        if (localInstance == null) {
            synchronized (DataBaseHandler.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBaseHandler();
                }
            }
        }
        return localInstance;
    }

    private DataBaseHandler() {
    }

    public void initialize(ConfigDB configNew) throws SQLException, NullPointerException {
        if (isDBHaveConfig())
            return;

        setConfig(configNew);
        if (config.get_DB_Driver().equalsIgnoreCase("sqlite")) {
            Path dbPath = Paths.get("").toAbsolutePath().resolve(config.get_DB_SQLite_FileName());
            SQLiteDataSource ds = new SQLiteDataSource();
            String dbURL = "jdbc:sqlite:" + dbPath;

            ds.setUrl(dbURL);
            this.connection = ds.getConnection();
        }
        if (config.get_DB_Driver().equalsIgnoreCase("mysql")) {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + config.get_DB_MySQL_Host() + '/' + config.get_DB_MySQL_DataBaseTitle()
                            + "?useUnicode="+config.get_DB_MySQL_UseUnicode()
                            +"&useLegacyDatetimeCode="+config.get_DB_MySQL_UseLegacyDatetimeCode()
                            +"&serverTimezone="+config.get_DB_MySQL_ServerTimeZone()
                            +"&useSSL="+config.get_DB_MySQL_UseSSL()
                            +"&verifyServerCertificate="+config.get_DB_MySQL_VerifyServerCertificate()
                            +"&requireSSL="+config.get_DB_MySQL_RequireSSL()
                    , config.get_DB_MySQL_User_Name()
                    , config.get_DB_MySQL_User_Password());
        }
    }


    /**
     * Создание таблицы
     *
     * @throws SQLException
     */
    private void createTable(Model model) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println(model.getCreateTableQuery());
            statement.execute(model.getCreateTableQuery());
        }
    }

    /**
     * Добавление записи
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public int insert(Model model) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getInsertQuery());
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            } else {
                System.out.println("Row affected. Last id: " + affectedRows);
                return affectedRows;
            }
        }
    }

    /**
     * Изменение записи
     *
     * @param model
     * @throws SQLException
     */
    public int update(Model model) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getUpdateQuery());
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected.");
            } else {
                System.out.println("Row changed. Last id: " + affectedRows);
                return affectedRows;
            }
        }
    }


    /**
     * Получение всех данных из таблиц согласно селекторам и вхождениям
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> List<Map<String, String>> get(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT " + t.getSelectors() + " FROM " + t.getTableNameFix() + t.getJoin() + t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultMapList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Получение первой записи из таблиц согласно селекторам и вхождениям
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> Map<String, String> getFirst(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT " + t.getSelectors() + " FROM " + t.getTableNameFix() + t.getJoin() + t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return t.getResultMap(resultSet);
            else
                return null;
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Получение всех записей из таблицы согласно модели в параметре
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> List<T> getObjects(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + t.getTableNameFix() + t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Получение первой записи из таблицы согласно модели в параметре
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> T getObject(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + t.getTableNameFix() + t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return (T) t.getResult(resultSet);
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Установка файла конфигурации
     *
     * @param config
     */
    private void setConfig(ConfigDB config) {
        this.config = config;
    }

    public boolean isDBHaveConfig() {
        return config != null;
    }


    public static char getQuotes() {
        String driver = instance.config.get_DB_Driver();
        return driver.equalsIgnoreCase("mysql") ? '`' : driver.equalsIgnoreCase("sqlite") ? '\"' : ' ';
    }
}
