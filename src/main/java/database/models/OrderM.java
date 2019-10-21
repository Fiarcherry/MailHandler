package database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public String getId() {
        return id;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getDate() {
        return date;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getOurCommission() {
        return ourCommission;
    }

    public String getData() {
        return data;
    }


    public OrderM(){

    }

    public OrderM(String id){
        this.id = id;
    }

    public OrderM(String id, String idClient, String date, Float amount, Float ourCommission, String data) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.amount = amount;
        this.ourCommission = ourCommission;
        this.data = data;
    }

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
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) " +
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
        String query = String.format("CREATE TABLE if not exists '%s' (" +
                        "'%s' TEXT PRIMARY KEY, " +
                        "'%s' TEXT, " +
                        "'%s' TEXT, " +
                        "'%s' REAL, " +
                        "'%s' REAL, " +
                        "'%s' TEXT, " +
                        "FOREIGN KEY (" + OrderM.ID_CLIENT_DEF + ") " +
                        "REFERENCES " + ClientM.TABLE_NAME + " (" + ClientM.ID_DEF +"));",
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
        String query = String.format("update %s set " +
                        "%s = \"%s\", " +
                        "%s = \"%s\", " +
                        "%s = \"%s\", " +
                        "%s = \"%s\", " +
                        "%s = \"%s\" " +
                        "where %s = \"%s\"",
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
    public String getSelectAllQuery() {
        String query = "SELECT * FROM " + OrderM.TABLE_NAME;

        System.out.println(query);
        return query;
    }

    @Override
    public String getSelectFirstQuery() {
        String query = String.format("SELECT * FROM %s " +
                "WHERE %s = \"%s\"", OrderM.TABLE_NAME, OrderM.ID_DEF, this.getPrimaryKey());

        System.out.println(query);
        return query;
    }

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public String getTableName() {
        return OrderM.TABLE_NAME;
    }

    @Override
    public List getResultList(ResultSet resultSet) throws SQLException {
        List<OrderM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new OrderM(resultSet));
        }
        return rows;
    }

    @Override
    public Model getResult(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString(OrderM.ID_DEF);
        this.idClient = resultSet.getString(OrderM.ID_CLIENT_DEF);
        this.date = resultSet.getString(OrderM.DATE_DEF);
        this.amount = resultSet.getFloat(OrderM.AMOUNT_DEF);
        this.ourCommission = resultSet.getFloat(OrderM.OUR_COMMISSION_DEF);
        this.data = resultSet.getString(OrderM.DATA_DEF);
        return this;
    }

    @Override
    public OrderM removeCondition(String key) {
        super.removeCondition(key);
        return this;
    }
    @Override
    public OrderM addCondition(String key, String value) {
        super.addCondition(key, value);
        return this;
    }
    @Override
    public OrderM addCondition(String key, String value, boolean isText) {
        super.addCondition(key, value, isText);
        return this;
    }
    @Override
    public OrderM removeAllConditions() {
        super.removeAllConditions();
        return this;
    }

    @Override
    public OrderM removeJoin(String key) {
        super.removeJoin(key);
        return this;
    }
    @Override
    public OrderM addJoin(String tableDef, String primaryKey, String foreignKey) {
        super.addJoin(tableDef, primaryKey, foreignKey);
        return this;
    }
    @Override
    public OrderM removeAllJoins() {
        super.removeAllJoins();
        return this;
    }

    @Override
    public OrderM removeSelector(String tableName, String columnName) {
        super.removeSelector(tableName, columnName);
        return this;
    }
    @Override
    public OrderM addSelector(String tableName, String columnName) {
        super.addSelector(tableName, columnName);
        return this;
    }
    @Override
    public OrderM addSelector(String tableName, String columnName, String columnMask) {
        super.addSelector(tableName, columnName, columnMask);
        return this;
    }
    @Override
    public OrderM removeAllSelectors() {
        super.removeAllSelectors();
        return this;
    }
}
