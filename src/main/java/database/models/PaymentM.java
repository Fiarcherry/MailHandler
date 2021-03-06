package database.models;



import com.mpt.databasehandler.DataBaseHandler;
import com.mpt.databasehandler.JoinType;
import com.mpt.databasehandler.Model;
import com.mpt.databasehandler.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель для платежей
 */
public class PaymentM extends Model {

    public static final String TABLE_NAME = "Payment";

    public static final String ID_DEF = "Id";
    public static final String ID_ORDER_DEF = "IdOrder";
    public static final String DATE_DEF = "DatePayment";
    public static final String AMOUNT_DEF = "AmountPayment";
    public static final String BANK_COMMISSION_DEF = "BankCommission";
    public static final String IS_PROCESSED_DEF = "IsProcessed";

    private String id;
    private String idOrder;
    private String date;
    private Float amount;
    private Float bankCommission;
    private Boolean isProcessed;

    /**
     * Чтение ключа
     * @return Ключ
     */
    public String getId() {
        return id;
    }

    /**
     * Чтение ключа заказа
     * @return Ключ заказа
     */
    public String getIdOrder() {
        return idOrder;
    }

    /**
     * Чтение даты
     * @return Дата
     */
    public String getDate() {
        return date;
    }

    /**
     * Чтение стоимости
     * @return
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * Чтение комиссии банка
     * @return
     */
    public Float getBankCommission() {
        return bankCommission;
    }

    /**
     * Чтение статуса платежа
     * @return Отправлен ли платёж
     */
    public Boolean getProcessed() {
        return isProcessed;
    }

    /**
     * Конструктор модели платежа
     */
    public PaymentM() {
    }

    /**
     * Конструктор модели платежа
     * @param id Ключ
     */
    public PaymentM(String id) {
        this.id = id;
    }

    /**
     * Конструктор модели платежа
     * @param id Ключ
     * @param idOrder Ключ заказа
     * @param date Дата
     * @param amount Стоимость
     * @param bankCommission Комиссия банка
     * @param isProcessed Статус платежа
     */
    public PaymentM(String id, String idOrder, String date, Float amount, Float bankCommission, Boolean isProcessed) {
        this.id = id;
        this.idOrder = idOrder;
        this.date = date;
        this.amount = amount;
        this.bankCommission = bankCommission;
        this.isProcessed = isProcessed;
    }

    /**
     * Конструктор модели платежа
     * @param id Ключ
     * @param idOrder Ключь заказа
     * @param date Дата
     * @param amount Стоимость
     * @param bankCommission Комиссия банка
     */
    public PaymentM(String id, String idOrder, String date, Float amount, Float bankCommission) {
        this.id = id;
        this.idOrder = idOrder;
        this.date = date;
        this.amount = amount;
        this.bankCommission = bankCommission;
        this.isProcessed = false;
    }

    /**
     * Конструктор модели платежа
     * @param resultSet Данные из базы данных
     * @throws SQLException
     */
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
        return String.format("INSERT INTO `%s` (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) " +
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
        return String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` TEXT PRIMARY KEY, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` REAL, " +
                        "`%s` REAL, " +
                        "`%s` INTEGER DEFAULT 0, " +
                        "FOREIGN KEY (`" + PaymentM.ID_ORDER_DEF + "`) " +
                        "REFERENCES `" + OrderM.TABLE_NAME + "` (`" + OrderM.ID_DEF + "`));",
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
        String query = String.format("update `%s` set " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = %s, " +
                        "`%s` = %s, " +
                        "`%s` = %s " +
                        "where `%s` = \"%s\"",
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


    public static PaymentM getPayment(String uni) {
        return (PaymentM) DataBaseHandler.getInstance().getObject(new PaymentM().addCondition(PaymentM.ID_DEF, uni, true));

    }

    public static void updateChecked(PaymentM payment) throws SQLException{
        payment.setProcessedTrue();
        DataBaseHandler.getInstance().update(payment);
    }

    public void setProcessedTrue() {
        this.isProcessed = true;
    }

    @Override
    public String toString() {
        return id + isProcessed.toString();
    }

    //region Conditions
    @Override
    public Model removeCondition(String key) {
        super.removeCondition(key);
        return this;
    }

    @Override
    public Model addCondition(String key, String value) {
        super.addCondition(key, value);
        return this;
    }

    @Override
    public Model addCondition(String key, String value, boolean isText) {
        super.addCondition(key, value, isText);
        return this;
    }

    @Override
    public Model addCondition(String table, String column, String value, boolean isText) {
        super.addCondition(table, column, value, isText);
        return this;
    }

    @Override
    public Model removeAllConditions() {
        super.removeAllConditions();
        return this;
    }
    //endregion

    //region Joins
    @Override
    public Model removeJoin(String key) {
        super.removeJoin(key);
        return this;
    }

    @Override
    public Model addJoin(JoinType joinType, String foreignTable, String primaryKey, String primaryTable, String foreignKey) {
        super.addJoin(joinType, foreignTable, primaryKey, primaryTable, foreignKey);
        return this;
    }

    @Override
    public Model addJoin(JoinType joinType, String foreignTable, String primaryKey, String foreignKey) {
        super.addJoin(joinType, foreignTable, primaryKey, foreignKey);
        return this;
    }

    @Override
    public Model removeAllJoins() {
        super.removeAllJoins();
        return this;
    }
    //endregion

    //region Selectors
    @Override
    public Model removeSelector(String tableName, String columnName) {
        super.removeSelector(tableName, columnName);
        return this;
    }

    @Override
    public Model addSelector(String tableName, String columnName) {
        super.addSelector(tableName, columnName);
        return this;
    }

    @Override
    public Model addSelector(Selector selector) {
        super.addSelector(selector);
        return this;
    }

    @Override
    public Model addSelector(String tableName, String columnName, String columnMask) {
        super.addSelector(tableName, columnName, columnMask);
        return this;
    }

    @Override
    public Model removeAllSelectors() {
        super.removeAllSelectors();
        return this;
    }
    //endregion
}
