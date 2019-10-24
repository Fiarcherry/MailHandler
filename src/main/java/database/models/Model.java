package database.models;

import database.query.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Model <T extends Model>{
    private NavigableMap<String, String> conditions = new TreeMap<>();
    private Map<String, String> joins = new HashMap<>();
    private List<Selector> selectors = new ArrayList<>();

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
    public T addJoin(String connectableTableName, String primaryKey, String foreignTableName, String foreignKey){
        joins.put('\"'+ connectableTableName +"\" `"+ connectableTableName+"`", "\""+ connectableTableName +"\".\""+primaryKey+"\" = \""+foreignTableName+"\".\""+foreignKey+'\"');
        return null;
    }
    public T addJoin(String connectableTableName, String primaryKey, String foreignKey){
        joins.put('\"'+ connectableTableName +"\" "+ connectableTableName, '\"'+ connectableTableName +"\".\""+primaryKey+"\" = \""+getTableName()+"\".\""+foreignKey+'\"');
        return null;
    }
    public T removeAllJoins(){
        joins.clear();
        return null;
    }

    public T removeSelector(String tableName, String columnName){
        selectors.remove(new Selector(tableName, columnName));
        return null;
    }
    public T addSelector(String tableName, String columnName){
        selectors.add(new Selector(tableName, columnName));
        System.out.println("add selector: "+tableName+"->"+columnName);
        return null;
    }
    public T addSelector(Selector selector){
        selectors.add(selector);
        System.out.println("add selector: "+selector.toString());
        return null;
    }
    public T addSelector(String tableName, String columnName, String columnMask){
        selectors.add(new Selector(tableName, columnName, columnMask));
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
        Selector last = selectors.get(selectors.size()-1);
        for (Selector entry: selectors) {
            query.append(entry.getSelector());
            if (!last.equals(entry)){
                query.append(", ");
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

    public abstract String getPrimaryKey();
    public abstract String getTableName();
    public String getTableNameFix(){
        return '\"'+getTableName()+'\"';
    }

    public abstract List<T> getResultList(ResultSet resultSet) throws SQLException;
    public abstract T getResult(ResultSet resultSet) throws SQLException;

    public final Map<String, String> getResultMap(ResultSet resultSet) throws SQLException{
        Map<String, String> result = new HashMap<>();
        for (Selector selector: selectors) {
            result.put(selector.getColumnName(), resultSet.getString(selector.getColumnName()));
        }
        return result;
    }
    public final List<Map<String, String>> getResultMapList(ResultSet resultSet) throws SQLException{
        List<Map<String, String>> result = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> row = getResultMap(resultSet);
            if (!row.isEmpty())
                result.add(row);
        }
        return result;
    }
}
