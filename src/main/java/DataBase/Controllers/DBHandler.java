package DataBase.Controllers;

import DataBase.Models.PaymentM;
import org.sqlite.SQLiteDataSource;

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
     * Создание таблиц и их заполнение первоначальными данными при отсутствии в базе
     * @throws SQLException
     */
    private void createTable() throws SQLException{
        String createQuery = String.format("CREATE TABLE if not exists %s (%s TEXT PRIMARY KEY, %s INTEGER, %s TEXT, %s INTEGER, %s REAL, %s REAL);",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF);
        try(Statement statement = connection.createStatement()){
            statement.execute(createQuery);
            writeData();
        }

    }

    private void writeData() throws SQLException{

    }

    public void addPayment(PaymentM payment) throws SQLException{
        String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF);
        try(PreparedStatement statement = connection.prepareStatement(insertQuery)){
            statement.setObject(1, payment.getUri());
            statement.setObject(2, payment.getNumber());
            statement.setObject(3, payment.getDateOperation());
            statement.setObject(4, payment.getAccount());
            statement.setObject(5, payment.getAmount());
            statement.setObject(6, payment.getCommission());
            statement.execute();
        }
    }

    public List<PaymentM> getAllPayments(){
        try(Statement statement = connection.createStatement()){
            List<PaymentM> payments = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT ALL FROM %s", PaymentM.TABLE_NAME));
            while(resultSet.next()){
                payments.add(new PaymentM(resultSet.getString(PaymentM.UNI_DEF),
                        resultSet.getInt(PaymentM.NUMBER_DEF),
                        resultSet.getString(PaymentM.DATE_OPERATION_DEF),
                        resultSet.getInt(PaymentM.ACCOUNT_DEF),
                        resultSet.getFloat(PaymentM.AMOUNT_DEF),
                        resultSet.getFloat(PaymentM.COMMISSION_DEF)));
            }
            return payments;
        }
        catch (SQLException e){
            return null;
        }
    }
}
