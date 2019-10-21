package database.controllers;

import database.models.Model;
import database.models.PaymentM;
import database.models.UserM;
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
            if (PaymentM.TABLE_NAME.equals(rs.getString("TABLE_NAME")) || UserM.TABLE_NAME.equals(rs.getString("TABLE_NAME")))
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
    public <T extends Model> List<T> getAll(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableName()+t.getJoin()+t.getWhere("AND");
            ResultSet resultSet = statement.executeQuery(query);
            return t.getResultList(resultSet);
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
    public <T extends Model> T getFirst(T t) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT "+t.getSelectors()+" FROM "+t.getTableName()+t.getJoin()+t.getWhere("AND");
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            return (T) t.getResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
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
        createTable(firstPayment);
        createTable(firstUser);
        insert(firstPayment);
        insert(firstUser);
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
