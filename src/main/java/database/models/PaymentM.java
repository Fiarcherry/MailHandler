package database.models;

import database.controllers.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentM extends Model {

    public static final String TABLE_NAME = "`Payment`";

    public static final String ID_DEF = "`Id`";
    public static final String ID_ORDER_DEF = "`IdOrder`";
    public static final String DATE_DEF = "`DatePayment`";
    public static final String AMOUNT_DEF = "`AmountPayment`";
    public static final String BANK_COMMISSION_DEF = "`BankCommission`";
    public static final String IS_PROCESSED_DEF = "`IsProcessed`";

    private String id;
    private String idOrder;
    private String date;
    private Float amount;
    private Float bankCommission;
    private Boolean isProcessed;

    public String getId() {
        return id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getDate() {
        return date;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getBankCommission() {
        return bankCommission;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }


    public PaymentM() {
    }

    public PaymentM(String id) {
        this.id = id;
    }

    public PaymentM(String id, String idOrder, String date, Float amount, Float bankCommission, Boolean isProcessed) {
        this.id = id;
        this.idOrder = idOrder;
        this.date = date;
        this.amount = amount;
        this.bankCommission = bankCommission;
        this.isProcessed = isProcessed;
    }

    public PaymentM(String id, String idOrder, String date, Float amount, Float bankCommission) {
        this.id = id;
        this.idOrder = idOrder;
        this.date = date;
        this.amount = amount;
        this.bankCommission = bankCommission;
        this.isProcessed = false;
    }

    public PaymentM(ResultSet resultSet) throws SQLException {
        this(resultSet.getString(PaymentM.ID_DEF),
                resultSet.getString(PaymentM.ID_ORDER_DEF),
                resultSet.getString(PaymentM.DATE_DEF),
                resultSet.getFloat(PaymentM.AMOUNT_DEF),
                resultSet.getFloat(PaymentM.BANK_COMMISSION_DEF),
                resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF));
    }


    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) " +
                        "VALUES (\"%s\", \"%s\", \"%s\", %s, %s, %s)",
                PaymentM.TABLE_NAME,
                PaymentM.ID_DEF,
                PaymentM.ID_ORDER_DEF,
                PaymentM.DATE_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.BANK_COMMISSION_DEF,
                PaymentM.IS_PROCESSED_DEF,
                this.getId(),
                this.getIdOrder(),
                this.getDate(),
                this.getAmount(),
                this.getBankCommission(),
                this.getProcessed() ? 1 : 0);
    }

    @Override
    public String getCreateTableQuery() {
        return String.format("CREATE TABLE if not exists %s (" +
                        "%s TEXT PRIMARY KEY, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s REAL, " +
                        "%s REAL, " +
                        "%s INTEGER DEFAULT 0, " +
                        "FOREIGN KEY (" + PaymentM.ID_ORDER_DEF + ") " +
                        "REFERENCES " + OrderM.TABLE_NAME + " (" + OrderM.ID_DEF +"));",
                PaymentM.TABLE_NAME,
                PaymentM.ID_DEF,
                PaymentM.ID_ORDER_DEF,
                PaymentM.DATE_DEF,
                PaymentM.AMOUNT_DEF,
                PaymentM.BANK_COMMISSION_DEF,
                PaymentM.IS_PROCESSED_DEF);
    }

    @Override
    public String getUpdateQuery() {
        String query = String.format("update %s set " +
                        "%s = \"%s\", " +
                        "%s = \"%s\", " +
                        "%s = %s, " +
                        "%s = %s, " +
                        "%s = %s " +
                        "where %s = \"%s\"",
                PaymentM.TABLE_NAME,
                PaymentM.ID_ORDER_DEF,
                this.getIdOrder(),
                PaymentM.DATE_DEF,
                this.getDate(),
                PaymentM.AMOUNT_DEF,
                this.getAmount(),
                PaymentM.BANK_COMMISSION_DEF,
                this.getBankCommission(),
                PaymentM.IS_PROCESSED_DEF,
                this.getProcessed() ? 1 : 0,
                PaymentM.ID_DEF,
                this.getId());

        System.out.println(query);
        return query;
    }


    @Override
    public String getPrimaryKey() {
        return this.id;
    }
    @Override
    public String getTableName() {
        return PaymentM.TABLE_NAME;
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
        this.id = resultSet.getString(PaymentM.ID_DEF);
        this.idOrder = resultSet.getString(PaymentM.ID_ORDER_DEF);
        this.date = resultSet.getString(PaymentM.DATE_DEF);
        this.amount = resultSet.getFloat(PaymentM.AMOUNT_DEF);
        this.bankCommission = resultSet.getFloat(PaymentM.BANK_COMMISSION_DEF);
        this.isProcessed = resultSet.getBoolean(PaymentM.IS_PROCESSED_DEF);
        return this;
    }


    @Override
    public PaymentM removeCondition(String key) {
        super.removeCondition(key);
        return this;
    }
    @Override
    public PaymentM addCondition(String key, String value) {
        super.addCondition(key, value);
        return this;
    }
    @Override
    public PaymentM addCondition(String key, String value, boolean isText) {
        super.addCondition(key, value, isText);
        return this;
    }
    @Override
    public PaymentM removeAllConditions() {
        super.removeAllConditions();
        return this;
    }

    @Override
    public PaymentM removeJoin(String key) {
        super.removeJoin(key);
        return this;
    }
    @Override
    public PaymentM addJoin(String tableDef, String primaryKey, String foreignKey) {
        super.addJoin(tableDef, primaryKey, foreignKey);
        return this;
    }
    @Override
    public PaymentM removeAllJoins() {
        super.removeAllJoins();
        return this;
    }

    @Override
    public PaymentM removeSelector(String tableName, String columnName) {
        super.removeSelector(tableName, columnName);
        return this;
    }
    @Override
    public PaymentM addSelector(String tableName, String columnName) {
        super.addSelector(tableName, columnName);
        return this;
    }
    @Override
    public PaymentM addSelector(String tableName, String columnName, String columnMask) {
        super.addSelector(tableName, columnName, columnMask);
        return this;
    }
    @Override
    public PaymentM removeAllSelectors() {
        super.removeAllSelectors();
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
            payments[i] = DBHandler.getInstance().getFirst(new PaymentM(unis[i]).addCondition(PaymentM.ID_DEF, unis[i], true));
        return payments;
    }

    public static void updateChecked(PaymentM[] payments) throws SQLException {
        for (PaymentM payment : payments) {
            payment.setProcessedTrue();
            DBHandler.getInstance().update(payment);
        }
    }

    public void setProcessedTrue() {
        this.isProcessed = true;
    }

    @Override
    public String toString() {
        return id + isProcessed.toString();
    }

//TODO Начать с этого
//    public String getAccount(String id) throws SQLException {
//        String idClient = DBHandler.getInstance().getFirst(new PaymentM().addJoin(OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.ID_ORDER_DEF).addJoin(ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.ID_CLIENT_DEF).addSelector(ClientM.TABLE_NAME, ClientM.ID_DEF).addCondition(PaymentM.ID_DEF, id, true));
//
//        return idClient;
//    }
}
