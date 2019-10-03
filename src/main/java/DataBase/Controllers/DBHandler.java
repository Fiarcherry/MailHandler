package DataBase.Controllers;

import DataBase.Models.PaymentM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sqlite.SQLiteDataSource;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
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
        String dbURL = "jdbc:sqlite:"+dbPath;

        ds.setUrl(dbURL);
        this.connection = ds.getConnection();
        ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
        boolean isDbEmpty = true;
        while (rs.next()){
            if (PaymentM.TABLE_NAME.equals(rs.getString("TABLE_NAME")))
                isDbEmpty = false;
        }
        if (isDbEmpty)
            createTable();
    }

    /**
     * Первоначальное создание таблицы
     * @throws SQLException
     */
    private void createTable() throws SQLException{
        String createQuery = String.format("CREATE TABLE if not exists '%s' ('%s' TEXT PRIMARY KEY, '%s' TEXT, '%s' TEXT, '%s' TEXT, '%s' REAL, '%s' REAL, '%s' TEXT DEFAULT \"test@bg.mail\", '%s' INTEGER DEFAULT 0);",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF,
                PaymentM.EMAIL_DEF,
                PaymentM.IS_PROCESSED_DEF);
        Statement statement = connection.createStatement();
        statement.execute(createQuery);
        writeData();
    }

    /**
     * Запись первичных данных
     * @throws SQLException
     */
    private void writeData() throws SQLException{
        addPayment(new PaymentM("r6lpoptgkeki9l14zu24hiapw", "1197145776", "2019-09-25T00:18:59", "118469534609", 70478.14f, 204.39f, "test@bg.market", false));
    }

    /**
     * Добавление нового платежа
     * @param payment
     * @throws SQLException
     */
    public void addPayment(PaymentM payment) throws SQLException{
        String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %s, %s, \"%s\", %s)",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF,
                PaymentM.EMAIL_DEF,
                PaymentM.IS_PROCESSED_DEF,
                payment.getUni(),
                payment.getNumber(),
                payment.getDateOperation(),
                payment.getAccount(),
                payment.getAmount(),
                payment.getCommission(),
                payment.getEmail() == null?"test@bg.market":payment.getEmail(),
                payment.getProcessed()?1:0);
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertQuery);
        statement.close();
    }


    /**
     * Изменение платежа по Uni
     * @param payment
     * @throws SQLException
     */
    public void updatePayment(PaymentM payment) throws SQLException{
        String updateQuery = String.format("update %s set %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = %s, %s = %s, %s = \"%s\", %s = %s where %s = \"%s\"",
                PaymentM.TABLE_NAME,
                PaymentM.NUMBER_DEF,
                payment.getNumber(),
                PaymentM.DATE_OPERATION_DEF,
                payment.getDateOperation(),
                PaymentM.ACCOUNT_DEF,
                payment.getAccount(),
                PaymentM.AMOUNT_DEF,
                payment.getAmount(),
                PaymentM.COMMISSION_DEF,
                payment.getCommission(),
                PaymentM.EMAIL_DEF,
                payment.getEmail() == null?"test@bg.market":payment.getEmail(),
                PaymentM.IS_PROCESSED_DEF,
                payment.getProcessed()?1:0,
                PaymentM.UNI_DEF,
                payment.getUni());
        Statement statement = connection.createStatement();
        statement.executeUpdate(updateQuery);
        statement.close();
    }

    public void updatePayment(Boolean state, String uni) throws SQLException{
            Statement statement = connection.createStatement();
            String updateQuery = String.format("update %s set %s = %s where %s = \"%s\"",
                    PaymentM.TABLE_NAME,
                    PaymentM.IS_PROCESSED_DEF,
                    state ? 1 : 0,
                    PaymentM.UNI_DEF,
                    uni);
            System.out.println(updateQuery);
            statement.executeUpdate(updateQuery);
            statement.close();
        }

    public void updateChecked(PaymentM[] payments) throws SQLException{
        for (PaymentM payment: payments) {
            payment.switchProcessed();
            updatePayment(payment.getProcessed(), payment.getUni());
        }
    }


    /**
     * Получение платежа по Uni
     * @param uni
     * @return
     */
    public PaymentM getPayment(String uni){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s = \"%s\"", PaymentM.TABLE_NAME, PaymentM.UNI_DEF, uni));
            PaymentM payment = null;
            while(resultSet.next()){
                payment = new PaymentM(resultSet.getString(PaymentM.UNI_DEF),
                        resultSet.getString(PaymentM.NUMBER_DEF),
                        resultSet.getString(PaymentM.DATE_OPERATION_DEF),
                        resultSet.getString(PaymentM.ACCOUNT_DEF),
                        resultSet.getFloat(PaymentM.AMOUNT_DEF),
                        resultSet.getFloat(PaymentM.COMMISSION_DEF),
                        resultSet.getString(PaymentM.EMAIL_DEF),
                        resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF));
            }
            return payment;
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
            payments[i] = getPayment(unis[i]);
        return payments;
    }

    /**
     * Получение всех платежей
     * @return
     */
    public List<PaymentM> getPayments(){
        try(Statement statement = connection.createStatement()){
            List<PaymentM> payments = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", PaymentM.TABLE_NAME));
            while(resultSet.next()){
                payments.add(new PaymentM(resultSet.getString(PaymentM.UNI_DEF),
                        resultSet.getString(PaymentM.NUMBER_DEF),
                        resultSet.getString(PaymentM.DATE_OPERATION_DEF),
                        resultSet.getString(PaymentM.ACCOUNT_DEF),
                        resultSet.getFloat(PaymentM.AMOUNT_DEF),
                        resultSet.getFloat(PaymentM.COMMISSION_DEF),
                        resultSet.getString(PaymentM.EMAIL_DEF),
                        resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF)));
            }
            return payments;
        }
        catch (SQLException e){
            return null;
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
