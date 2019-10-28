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
        int tableCount = 5;

        Path dbPath = Paths.get("").toAbsolutePath().resolve(DB_FILE_NAME);
        SQLiteDataSource ds = new SQLiteDataSource();
        String dbURL = "jdbc:sqlite:"+dbPath;

        ds.setUrl(dbURL);
        this.connection = ds.getConnection();
        ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
        boolean[] exists = new boolean[tableCount];
        while (rs.next())
            switch (rs.getString("TABLE_NAME")){
                case ClientM.TABLE_NAME:
                    exists[0] = true;
                    break;
                case OrderM.TABLE_NAME:
                    exists[1] = true;
                    break;
                case PaymentM.TABLE_NAME:
                    exists[2] = true;
                    break;
                case UserM.TABLE_NAME:
                    exists[3] = true;
                    break;
                case ErrorM.TABLE_NAME:
                    exists[4] = true;
                    break;
                default:
                    break;
            }
        createTables(exists);
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
            if (affectedRows == 0){
                throw new SQLException("Insert failed, no rows affected.");
            }
            else{
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
            if (affectedRows == 0){
                throw new SQLException("Update failed, no rows affected.");
            }
            else{
                return affectedRows;
            }
        }
    }


    /**
     * Получение всех записей из таблицы
     * @param t
     * @param <T>
     * @return
     */
    public <T extends Model> List<Map<String, String>> get(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableNameFix()+t.getJoin()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultMapList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Получение записи по первичному ключу объекта в параметре
     *
     * @param t
     * @return Возвращается ссылка на объект, переданный в параметрах
     */
    public <T extends Model> Map<String, String> getFirst(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableNameFix()+t.getJoin()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultMap(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Model> List<T> getObjects(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM "+t.getTableNameFix()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultList(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    public <T extends Model> T getObject(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM "+t.getTableNameFix()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return (T)t.getResult(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * Первоначальное создание таблиц
     *
     * @throws SQLException
     */
    private void createTables(boolean[] exists) throws SQLException {
        if (!exists[0]){
            ClientM firstClient = new ClientM("client1", "eryu", "kowjf", "p_a.s.nosach@mpt.ru");
            createTable(firstClient);
            insert(firstClient);
            System.out.println("Создана таблица клиентов.");
        }
        if (!exists[1]){
            OrderM firstOrder = new OrderM("order1", "client1", "2019-09-25T00:18:59"
                    , 70478.14f, 204.39f, "bjhwbfkqwhffhjksdjhfgwjhdfugw");
            createTable(firstOrder);
            insert(firstOrder);
            System.out.println("Создана таблица заказов.");
        }
        if (!exists[2]){
            PaymentM firstPayment = new PaymentM("r6lpoptgkeki9l14zu24hiapw", "order1", "2019-09-25T00:18:59"
                    , 70478.14f, 204.39f, false);
            createTable(firstPayment);
            insert(firstPayment);
            System.out.println("Создана таблица платежей.");
        }
        if (!exists[3]){
            UserM firstUser = new UserM("AAAAAAdmin", "admin", "admin", "p_a.s.nosach@mtp.ru");
            createTable(firstUser);
            insert(firstUser);
            System.out.println("Создана таблица пользователей.");
        }
        if (!exists[4]){
            ErrorM firstError= new ErrorM("Test Error");
            createTable(firstError);
            insert(firstError);
            System.out.println("Создана таблица ошибок.");
        }
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
