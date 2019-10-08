package DataBase.Models;

import DataBase.Controllers.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentM implements Model {

    public static final String TABLE_NAME = "Payment";

    public static final String UNI_DEF = "Uni";
    public static final String NUMBER_DEF = "Number";
    public static final String DATE_OPERATION_DEF = "DateOperation";
    public static final String ACCOUNT_DEF = "Account";
    public static final String AMOUNT_DEF = "Amount";
    public static final String COMMISSION_DEF = "Commission";
    public static final String EMAIL_DEF = "Email";
    public static final String IS_PROCESSED_DEF = "IsProcessed";

    private String uni;
    private String number;
    private String dateOperation;
    private String account;
    private Float amount;
    private Float commission;
    private String email;
    private Boolean isProcessed;

    public PaymentM() {
    }

    public PaymentM(String uni) {
        this.uni = uni;
    }

    public PaymentM(String uni, String number, String dateOperation, String account, Float amount, Float commission, String email, Boolean isProcessed) {
        this.uni = uni;
        this.number = number;
        this.dateOperation = dateOperation;
        this.account = account;
        this.amount = amount;
        this.commission = commission;
        this.email = email;
        this.isProcessed = isProcessed;
    }

    public PaymentM(String uni, String number, String dateOperation, String account, Float amount, Float commission) {
        this.uni = uni;
        this.number = number;
        this.dateOperation = dateOperation;
        this.account = account;
        this.amount = amount;
        this.commission = commission;
        this.email = null;
        this.isProcessed = false;
    }

    public PaymentM(ResultSet resultSet) throws SQLException {
        this(resultSet.getString(PaymentM.UNI_DEF),
                resultSet.getString(PaymentM.NUMBER_DEF),
                resultSet.getString(PaymentM.DATE_OPERATION_DEF),
                resultSet.getString(PaymentM.ACCOUNT_DEF),
                resultSet.getFloat(PaymentM.AMOUNT_DEF),
                resultSet.getFloat(PaymentM.COMMISSION_DEF),
                resultSet.getString(PaymentM.EMAIL_DEF),
                resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF));
    }


    public String getUni() {
        return uni;
    }

    public String getNumber() {
        return number;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public String getAccount() {
        return account;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getCommission() {
        return commission;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }


    public void switchProcessed() {
        this.isProcessed = !this.isProcessed;
    }

    public void setProcessedTrue() {
        this.isProcessed = true;
    }


    @Override
    public String toString() {
        return uni + isProcessed.toString();
    }

    @Override
    public String getPrimaryKey() {
        return this.uni;
    }

    @Override
    public String getCreateTableQuery() {
        return String.format("CREATE TABLE if not exists '%s' ('%s' TEXT PRIMARY KEY, '%s' TEXT, '%s' TEXT, '%s' TEXT, '%s' REAL, '%s' REAL, '%s' TEXT DEFAULT \"test@bg.mail\", '%s' INTEGER DEFAULT 0);",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF,
                PaymentM.EMAIL_DEF,
                PaymentM.IS_PROCESSED_DEF);
    }

    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %s, %s, \"%s\", %s)",
                PaymentM.TABLE_NAME,
                PaymentM.UNI_DEF,
                PaymentM.NUMBER_DEF,
                PaymentM.DATE_OPERATION_DEF,
                PaymentM.ACCOUNT_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.COMMISSION_DEF,
                PaymentM.EMAIL_DEF,
                PaymentM.IS_PROCESSED_DEF,
                this.getUni(),
                this.getNumber(),
                this.getDateOperation(),
                this.getAccount(),
                this.getAmount(),
                this.getCommission(),
                this.getEmail() == null ? "test@bg.market" : this.getEmail(),
                this.getProcessed() ? 1 : 0);
    }

    @Override
    public String getUpdateQuery() {
        return String.format("update %s set %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = %s, %s = %s, %s = \"%s\", %s = %s where %s = \"%s\"",
                PaymentM.TABLE_NAME,
                PaymentM.NUMBER_DEF,
                this.getNumber(),
                PaymentM.DATE_OPERATION_DEF,
                this.getDateOperation(),
                PaymentM.ACCOUNT_DEF,
                this.getAccount(),
                PaymentM.AMOUNT_DEF,
                this.getAmount(),
                PaymentM.COMMISSION_DEF,
                this.getCommission(),
                PaymentM.EMAIL_DEF,
                this.getEmail() == null ? "test@bg.market" : this.getEmail(),
                PaymentM.IS_PROCESSED_DEF,
                this.getProcessed() ? 1 : 0,
                PaymentM.UNI_DEF,
                this.getUni());
    }

    @Override
    public String getSelectFirstQuery() {
        return String.format("SELECT * FROM %s WHERE %s = \"%s\"", PaymentM.TABLE_NAME, PaymentM.UNI_DEF, getPrimaryKey());
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }


    @Override
    public List<PaymentM> getResultList(ResultSet resultSet) throws SQLException {
        List<PaymentM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new PaymentM(resultSet));
        }
        return rows;
    }

    @Override
    public PaymentM getResult(ResultSet resultSet) throws SQLException {
        this.uni = resultSet.getString(PaymentM.UNI_DEF);
        this.number = resultSet.getString(PaymentM.NUMBER_DEF);
        this.dateOperation = resultSet.getString(PaymentM.DATE_OPERATION_DEF);
        this.account = resultSet.getString(PaymentM.ACCOUNT_DEF);
        this.amount = resultSet.getFloat(PaymentM.AMOUNT_DEF);
        this.commission = resultSet.getFloat(PaymentM.COMMISSION_DEF);
        this.email = resultSet.getString(PaymentM.EMAIL_DEF);
        this.isProcessed = resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF);
        return this;
    }


    /**
     * Получение платежей по Uni
     *
     * @param unis
     * @return
     */
    public static PaymentM[] getPayments(String[] unis) throws SQLException{
        PaymentM[] payments = new PaymentM[unis.length];
        for (int i = 0; i < unis.length; i++)
            payments[i] = (PaymentM) DBHandler.getInstance().getByPrimaryKey(new PaymentM(unis[i]));
        return payments;
    }

    public static void updateChecked(PaymentM[] payments) throws SQLException {
        for (PaymentM payment : payments) {
            payment.setProcessedTrue();
            DBHandler.getInstance().update(payment);
        }
    }
}
