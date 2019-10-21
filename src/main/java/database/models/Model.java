package database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Model <T extends Model>{
    private NavigableMap<String, String> conditions = new TreeMap<>();
    private Map<String, String> joins = new TreeMap<>();
    private NavigableMap<String, String> selectors = new TreeMap<>();

    public T removeCondition(String key){
        conditions.remove(key);
        return null;
    }
    public T addCondition(String key, String value){
        conditions.put(key, value);
        return null;
    }
    public T addCondition(String key, String value, boolean isText){
        if (isText)
            conditions.put(key, Model.toText(value));
        else
            conditions.put(key,value);
        return null;
    }
    public T removeAllConditions(){
        conditions.clear();
        return null;
    }

    public T removeJoin(String key){
        conditions.remove(key);
        return null;
    }
    public T addJoin(String tableName, String primaryKey, String foreignKey){
        joins.put(tableName, tableName+'.'+primaryKey+" = "+getTableName()+'.'+foreignKey);
        return null;
    }
    public T removeAllJoins(){
        joins.clear();
        return null;
    }

    public T removeSelector(String tableName, String columnName){
        selectors.remove(tableName, columnName);
        return null;
    }
    public T addSelector(String tableName, String columnName){
        selectors.put(tableName, columnName);
        return null;
    }
    public T addSelector(String tableName, String columnName, String columnMask){
        selectors.put(tableName, columnName+" AS "+columnMask);
        return null;
    }
    public T removeAllSelectors(){
        selectors.clear();
        return null;
    }

    public static String toText(String value){
        return "\""+value+"\"";
    }

    public final String getSelectors(){
        StringBuilder query = new StringBuilder();
        if (selectors.isEmpty())
            return " * ";
        Map.Entry lastEntry = selectors.lastEntry();
        for (Map.Entry<String, String> entry: selectors.entrySet()) {
            query.append(entry.getKey()).append('.').append(entry.getValue());
            if (!lastEntry.equals(entry)){
                query.append(',');
            }
        }
        return query.toString();
    }
    public final String getWhere(String AndOr) {
        StringBuilder query = new StringBuilder();
        if (conditions.isEmpty())
            return ";";
        query.append(" WHERE ");
        Map.Entry lastEntry = conditions.lastEntry();
        for (Map.Entry<String, String> entry: conditions.entrySet()) {
            query.append(entry.getKey()).append(" = ").append(entry.getValue());
            if (!lastEntry.equals(entry)){
                query.append(" "+AndOr+" ");
            }
        }
        return query.toString()+';';
    }
    public final String getJoin(){
        StringBuilder query = new StringBuilder();
        if (joins.isEmpty())
            return "";
        for (Map.Entry<String, String> entry: joins.entrySet()) {
            query.append(" INNER JOIN ").append(entry.getKey()).append(" ON ").append(entry.getValue());
        }
        return query.toString();
    }

    public abstract String getInsertQuery();
    public abstract String getCreateTableQuery();
    public abstract String getUpdateQuery();
    public abstract String getSelectAllQuery();
    public abstract String getSelectFirstQuery();

    public abstract String getPrimaryKey();
    public abstract String getTableName();

    public abstract List<T> getResultList(ResultSet resultSet) throws SQLException;
    public abstract T getResult(ResultSet resultSet) throws SQLException;

}
