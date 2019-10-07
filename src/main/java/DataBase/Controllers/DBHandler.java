package DataBase.Controllers;

import DataBase.Models.Model;
import DataBase.Models.PaymentM;
import DataBase.Models.UserM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sqlite.SQLiteDataSource;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class DBHandler {

    private static final String DB_FILE_NAME = "mail_handler.db";


    private static DBHandler instance;

    private Connection connection;


    public static DBHandler getInstance() throws SQLException{
        DBHandler localInstance = instance;
        if (localInstance == null){
            synchronized (DBHandler.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DBHandler();
                }
            }
        }
        return localInstance;
    }

    private DBHandler() throws SQLException{

        Path dbPath = Paths.get("").toAbsolutePath().resolve(DB_FILE_NAME);
        SQLiteDataSource ds = new SQLiteDataSource();
        //String dbURL = "jdbc:sqlite:"+dbPath;
        String dbURL = "jdbc:sqlite:/var/lib/tomcat9/webapps/MailHandler/mail_handler.db";

        ds.setUrl(dbURL);
        this.connection = ds.getConnection();
        ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
        boolean isDbEmpty = true;
        while (rs.next()){
            if (PaymentM.TABLE_NAME.equals(rs.getString("TABLE_NAME")))
                isDbEmpty = false;
        }
        if (isDbEmpty)
            createTables();
    }


    /**
     * Создание таблицы
     * @throws SQLException
     */
    private void createTable(Model model) throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.execute(model.getCreateTableQuery());
        }
    }

    /**
     * Добавление записи
     * @param model
     * @return
     * @throws SQLException
     */
    public void insert(Model model) throws SQLException{
        try(Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getInsertQuery());
            if (affectedRows == 0)
                throw new SQLException("Insert failed, no rows affected.");
        }
    }

    /**
     * Изменение записи
     * @param model
     * @throws SQLException
     */
    public void update(Model model) throws SQLException{
        try(Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(model.getUpdateQuery());
            if (affectedRows == 0)
                throw new SQLException("Update failed, no rows affected.");
        }
    }

    /**
     * Получение всех записей из таблицы
     * @param model
     * @return
     */
    public List<Model> getAll(Model model){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(model.getSelectAllQuery());
            return model.getResultList(resultSet);
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Получение записи по первичному ключу объекта в параметре
     * @param model
     * @return Возвращается ссылка на объект, переданный в параметрах
     */
    public Model getByPrimaryKey(Model model){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(model.getSelectFirstQuery());
            if (resultSet.next())
                return model.getResult(resultSet);
            else
                return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Получение платежей по Uni
     * @param unis
     * @return
     */
    public PaymentM[] getPayments(String[] unis){
        PaymentM[] payments = new PaymentM[unis.length];
        for(int i = 0; i < unis.length; i++)
            payments[i] = (PaymentM)getByPrimaryKey(new PaymentM(unis[i]));
        return payments;
    }




    /**
     * Первоначальное создание таблиц
     * @throws SQLException
     */
    private void createTables() throws SQLException{
        PaymentM firstPayment = new PaymentM("r6lpoptgkeki9l14zu24hiapw", "1197145776", "2019-09-25T00:18:59", "118469534609", 70478.14f, 204.39f, "p_a.s.nosach@mpt.ru", false);
        UserM firstUser = new UserM("Админыч", "admin", "admin", "p_a.s.nosach@mtp.ru");
        createTable(firstPayment);
        createTable(firstUser);
        insert(firstPayment);
        insert(firstUser);
    }

    public void updateChecked(PaymentM[] payments) throws SQLException{
        for (PaymentM payment: payments) {
            payment.setProcessedTrue();
            update(payment);
        }
    }


    /**
     * Разбиение json строки на Uni
     * @param json
     * @return
     */
    public String[] parseUni(String json){
        Type itemsArrType = new TypeToken<String[]>() {}.getType();
        return new Gson().fromJson(json, itemsArrType);
    }
}
