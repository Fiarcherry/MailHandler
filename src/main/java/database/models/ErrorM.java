package database.models;

import database.query.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErrorM extends Model {

    public static final String TABLE_NAME = "Error";

    public static final String ID_DEF = "Id";
    public static final String MESSAGE_DEF = "Message";
    public static final String DATE_DEF = "Date";

    private String id;
    private String message;
    private String date;

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public ErrorM() {
    }

    public ErrorM(String message) {
        this.message = message;
    }

    public ErrorM(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public ErrorM(String id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public ErrorM(ResultSet resultSet) throws SQLException{
        this(resultSet.getString(ErrorM.ID_DEF), resultSet.getString(ErrorM.MESSAGE_DEF), resultSet.getString(ErrorM.DATE_DEF));
    }

    @Override
    public String getInsertQuery() {
        String query = String.format("INSERT INTO `%s` (`%s`, `%s`) " +
                        "VALUES (\"%s\", \"%s\")",
                ErrorM.TABLE_NAME,
                ErrorM.ID_DEF,
                ErrorM.MESSAGE_DEF,
                ErrorM.DATE_DEF,
                this.getId(),
                this.getMessage(),
                this.getDate());

        System.out.println(query);
        return query;
    }
    @Override
    public String getCreateTableQuery() {
        String query = String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` TEXT PRIMARY KEY, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT);",
                ErrorM.TABLE_NAME,
                ErrorM.ID_DEF,
                ErrorM.MESSAGE_DEF,
                ErrorM.DATE_DEF);

        System.out.println(query);
        return query;
    }
    @Override
    public String getUpdateQuery() {
        String query = String.format("update `%s` set " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\" " +
                        "where `%s` = \"%s\"",
                ErrorM.MESSAGE_DEF,
                this.getMessage(),
                ErrorM.DATE_DEF,
                this.getDate(),
                ErrorM.ID_DEF,
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
    public List<ErrorM> getResultList(ResultSet resultSet) throws SQLException {
        List<ErrorM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new ErrorM(resultSet));
        }
        return rows;
    }
    @Override
    public ErrorM getResult(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString(ErrorM.ID_DEF);
        this.message = resultSet.getString(ErrorM.MESSAGE_DEF);
        this.date = resultSet.getString(ErrorM.DATE_DEF);
        return this;
    }


    @Override
    public ErrorM removeCondition(String key) {
        super.removeCondition(key);
        return this;
    }
    @Override
    public ErrorM addCondition(String key, String value) {
        super.addCondition(key, value);
        return this;
    }
    @Override
    public ErrorM addCondition(String key, String value, boolean isText) {
        super.addCondition(key, value, isText);
        return this;
    }
    @Override
    public ErrorM removeAllConditions() {
        super.removeAllConditions();
        return this;
    }

    @Override
    public ErrorM removeJoin(String key) {
        super.removeJoin(key);
        return this;
    }
    @Override
    public ErrorM addJoin(String tableDef, String primaryKey, String foreignTableName, String foreignKey) {
        super.addJoin(tableDef, primaryKey, foreignTableName, foreignKey);
        return this;
    }
    @Override
    public ErrorM addJoin(String connectableTableName, String primaryKey, String foreignKey) {
        super.addJoin(connectableTableName, primaryKey, foreignKey);
        return this;
    }
    @Override
    public ErrorM removeAllJoins() {
        super.removeAllJoins();
        return this;
    }

    @Override
    public ErrorM removeSelector(String tableName, String columnName) {
        super.removeSelector(tableName, columnName);
        return this;
    }
    @Override
    public ErrorM addSelector(Selector selector) {
        super.addSelector(selector);
        return this;
    }
    @Override
    public ErrorM addSelector(String tableName, String columnName) {
        super.addSelector(tableName, columnName);
        return this;
    }
    @Override
    public ErrorM addSelector(String tableName, String columnName, String columnMask) {
        super.addSelector(tableName, columnName, columnMask);
        return this;
    }
    @Override
    public ErrorM removeAllSelectors() {
        super.removeAllSelectors();
        return this;
    }
}
