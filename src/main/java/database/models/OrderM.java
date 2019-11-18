package database.models;

import com.mpt.databasehandler.JoinType;
import com.mpt.databasehandler.Model;
import com.mpt.databasehandler.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель для заказов
 */
public class OrderM extends Model {

    public static final String TABLE_NAME = "Order";

    public static final String ID_DEF = "Id";
    public static final String ID_CLIENT_DEF = "IdClient";
    public static final String DATE_DEF = "Date";
    public static final String AMOUNT_DEF = "Amount";
    public static final String OUR_COMMISSION_DEF = "OurCommission";
    public static final String DATA_DEF = "Data";

    private String id;
    private String idClient;
    private String date;
    private Float amount;
    private Float ourCommission;
    private String data;

    /**
     * Чтение ключа
     * @return Ключ
     */
    public String getId() {
        return id;
    }

    /**
     * Чтение ключа клиента
     * @return Ключ клиента
     */
    public String getIdClient() {
        return idClient;
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
     * @return Стоимость
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * Чтение комиссии
     * @return Комиссия
     */
    public Float getOurCommission() {
        return ourCommission;
    }

    /**
     * Чтение данных
     * @return Данные
     */
    public String getData() {
        return data;
    }

    /**
     * Конструктор модели заказа
     */
    public OrderM(){

    }

    /**
     * Конструктор модели заказа
     * @param id Ключ
     */
    public OrderM(String id){
        this.id = id;
    }

    /**
     * Конструктор модели заказа
     * @param id Ключ
     * @param idClient Ключ клиента
     * @param date Дата
     * @param amount Стоимость
     * @param ourCommission Комиссия
     * @param data Данные
     */
    public OrderM(String id, String idClient, String date, Float amount, Float ourCommission, String data) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.amount = amount;
        this.ourCommission = ourCommission;
        this.data = data;
    }

    /**
     * Конструктор модели заказа
     * @param resultSet Данные из базы данных
     * @throws SQLException
     */
    public OrderM(ResultSet resultSet) throws SQLException {
        this(resultSet.getString(OrderM.ID_DEF),
                resultSet.getString(OrderM.ID_CLIENT_DEF),
                resultSet.getString(OrderM.DATE_DEF),
                resultSet.getFloat(OrderM.AMOUNT_DEF),
                resultSet.getFloat(OrderM.OUR_COMMISSION_DEF),
                resultSet.getString(OrderM.DATA_DEF));
    }


    @Override
    public String getInsertQuery() {
        String query = String.format("INSERT INTO `%s` (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) " +
                        "VALUES (\"%s\", \"%s\", \"%s\", %s, %s, \"%s\")",
                OrderM.TABLE_NAME,
                OrderM.ID_DEF,
                OrderM.ID_CLIENT_DEF,
                OrderM.DATE_DEF,
                OrderM.AMOUNT_DEF,
                OrderM.OUR_COMMISSION_DEF,
                OrderM.DATA_DEF,
                this.getId(),
                this.getIdClient(),
                this.getDate(),
                this.getAmount(),
                this.getOurCommission(),
                this.getData());

        System.out.println(query);
        return query;
    }
    @Override
    public String getCreateTableQuery() {
        String query = String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` TEXT PRIMARY KEY, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` REAL, " +
                        "`%s` REAL, " +
                        "`%s` TEXT, " +
                        "FOREIGN KEY (`" + OrderM.ID_CLIENT_DEF + "`) " +
                        "REFERENCES `" + ClientM.TABLE_NAME + "` (`" + ClientM.ID_DEF +"`));",
                OrderM.TABLE_NAME,
                OrderM.ID_DEF,
                OrderM.ID_CLIENT_DEF,
                OrderM.DATE_DEF,
                OrderM.AMOUNT_DEF,
                OrderM.OUR_COMMISSION_DEF,
                OrderM.DATA_DEF);

        System.out.println(query);
        return query;
    }
    @Override
    public String getUpdateQuery() {
        String query = String.format("update `%s` set " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\" " +
                        "where `%s` = \"%s\"",
                OrderM.TABLE_NAME,
                OrderM.ID_CLIENT_DEF,
                this.getIdClient(),
                OrderM.DATE_DEF,
                this.getDate(),
                OrderM.AMOUNT_DEF,
                this.getAmount(),
                OrderM.OUR_COMMISSION_DEF,
                this.getOurCommission(),
                OrderM.DATA_DEF,
                this.getData(),
                OrderM.ID_DEF,
                this.getId());

        System.out.println(query);
        return query;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public List<OrderM> getResultList(ResultSet resultSet) throws SQLException {
        List<OrderM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new OrderM(resultSet));
        }
        return rows;
    }
    @Override
    public OrderM getResult(ResultSet resultSet) throws SQLException {
        return new OrderM(resultSet.getString(OrderM.ID_DEF),
        resultSet.getString(OrderM.ID_CLIENT_DEF),
        resultSet.getString(OrderM.DATE_DEF),
        resultSet.getFloat(OrderM.AMOUNT_DEF),
        resultSet.getFloat(OrderM.OUR_COMMISSION_DEF),
        resultSet.getString(OrderM.DATA_DEF));
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
