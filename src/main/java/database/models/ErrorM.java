package database.models;

import com.mpt.databasehandler.*;
import com.mpt.databasehandler.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorM extends Model {

    public static final String TABLE_NAME = "Error";

    public static final String ID_DEF = "Id";
    public static final String MESSAGE_DEF = "Message";
    public static final String DATE_DEF = "Date";

    private Integer id;
    private String message;
    private String date;

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public ErrorM() {
    }

    public ErrorM(String message) {
        this.message = message;
        this.date = getCurrentDate();
    }

    public ErrorM(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public ErrorM(Integer id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public ErrorM(ResultSet resultSet) throws SQLException{
        this(resultSet.getInt(ErrorM.ID_DEF), resultSet.getString(ErrorM.MESSAGE_DEF), resultSet.getString(ErrorM.DATE_DEF));
    }

    @Override
    public String getInsertQuery() {
        String query = String.format("INSERT INTO `%s` (`%s`, `%s`) " +
                        "VALUES (\"%s\", \"%s\")",
                ErrorM.TABLE_NAME,
                ErrorM.MESSAGE_DEF,
                ErrorM.DATE_DEF,
                this.getMessage(),
                this.getDate());

        System.out.println(query);
        return query;
    }
    @Override
    public String getCreateTableQuery() {
        String query = String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` INTEGER PRIMARY KEY, " +
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
                        "where `%s` = %s",
                ErrorM.TABLE_NAME,
                ErrorM.MESSAGE_DEF,
                this.getMessage(),
                ErrorM.DATE_DEF,
                this.getDate(),
                ErrorM.ID_DEF,
                this.getId().toString());

        System.out.println(query);
        return query;
    }


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public String getPrimaryKey() {
        return this.id.toString();
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
        this.id = resultSet.getInt(ErrorM.ID_DEF);
        this.message = resultSet.getString(ErrorM.MESSAGE_DEF);
        this.date = resultSet.getString(ErrorM.DATE_DEF);
        return this;
    }

    public static void errorAdd(String message) throws SQLException {
        ErrorM error = new ErrorM(message, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")));
        DataBaseHandler.getInstance().insert(error);
    };

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
