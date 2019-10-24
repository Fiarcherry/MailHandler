package database.controllers;

import database.models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sqlite.SQLiteDataSource;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DBHandler {

    private static final String DB_FILE_NAME = "mail_handler.db";


    private static DBHandler instance;

    private Connection connection;

    public static DBHandler getInstance() throws SQLException {
        DBHandler localInstance = instance;
        if (localInstance == null) {
            synchronized (DBHandler.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DBHandler();
                }
            }
        }
        return localInstance;
    }

    private DBHandler() throws SQLException {

        Path dbPath = Paths.get("").toAbsolutePath().resolve(DB_FILE_NAME);
        SQLiteDataSource ds = new SQLiteDataSource();
        String dbURL = "jdbc:sqlite:"+dbPath;

        ds.setUrl(dbURL);
        this.connection = ds.getConnection();
        ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
        boolean isDbEmpty = true;
        while (rs.next()) {
            if (PaymentM.TABLE_NAME.equals(rs.getString("TABLE_NAME"))
                    || UserM.TABLE_NAME.equals(rs.getString("TABLE_NAME"))
                    || ClientM.TABLE_NAME.equals(rs.getString("TABLE_NAME"))
                    || OrderM.TABLE_NAME.equals(rs.getString("TABLE_NAME")))
                isDbEmpty = false;
        }
        if (isDbEmpty)
            createTables();
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
    public void insert(Model model) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getInsertQuery());
            if (affectedRows == 0)
                throw new SQLException("Insert failed, no rows affected.");
        }
    }

    /**
     * Изменение записи
     *
     * @param model
     * @throws SQLException
     */
    public void update(Model model) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getUpdateQuery());
            if (affectedRows == 0)
                throw new SQLException("Update failed, no rows affected.");
        }
    }


    /**
     * Получение всех записей из таблицы
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> List<Map<Selector, String>> get(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableName()+t.getJoin()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return t.getResultMapList(resultSet);
            else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Получение записи по первичному ключу объекта в параметре
     *
     * @param t
     * @return Возвращается ссылка на объект, переданный в параметрах
     */
    public <T extends Model> Map<Selector, String> getFirst(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableName()+t.getJoin()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return t.getResultMap(resultSet);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Model> List<T> getObjects(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM "+t.getTableName()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return t.getResultList(resultSet);
            else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public <T extends Model> T getObject(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM "+t.getTableName()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return (T)t.getResult(resultSet);
            else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }



    /**
     * Первоначальное создание таблиц
     *
     * @throws SQLException
     */
    private void createTables() throws SQLException {
        PaymentM firstPayment = new PaymentM("r6lpoptgkeki9l14zu24hiapw", "1197145776", "2019-09-25T00:18:59", 70478.14f, 204.39f, false);
        UserM firstUser = new UserM("AAAAAAdmin", "admin", "admin", "p_a.s.nosach@mtp.ru");
        OrderM firstOrder = new OrderM("order1", "client1", "2019-09-25T00:18:59", 70478.14f, 204.39f, "bjhwbfkqwhffhjksdjhfgwjhdfugw");
        ClientM firstClient = new ClientM("client1", "eryu", "kowjf", "p_a.s.nosach@mpt.ru");

        createTable(firstUser);
        insert(firstUser);
        createTable(firstClient);
        insert(firstClient);
        createTable(firstOrder);
        insert(firstOrder);
        createTable(firstPayment);
        insert(firstPayment);

    }

    /**
     * Разбиение json строки на Uni
     *
     * @param json
     * @return
     */
    public String[] parseUni(String json) {
        Type itemsArrType = new TypeToken<String[]>() {
        }.getType();
        return new Gson().fromJson(json, itemsArrType);
    }
}
