package com.mpt.databasehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Model <T extends Model>{
    static char quotes = DataBaseHandler.getInstance().isDBHaveConfig()?DataBaseHandler.getInstance().getQuotes():' ';

    private NavigableMap<String, String> conditions = new TreeMap<>();
    private List<Join> joins = new ArrayList<>();
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
    public T addJoin(JoinType joinType, String foreignTable, String primaryKey, String primaryTable, String foreignKey){
        joins.add(new Join(joinType, foreignTable, primaryKey, primaryTable, foreignKey, quotes));
        return null;
    }
    public T addJoin(JoinType joinType, String foreignTable, String primaryKey, String foreignKey){
        joins.add(new Join(joinType, foreignTable, primaryKey, getTableName(), foreignKey, quotes));
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
        return null;
    }
    public T addSelector(Selector selector){
        selectors.add(selector);
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
    private static String inQuotes(String value){
        return quotes+value+quotes;
    }

    final String getSelectors(){
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
    final String getWhere(String AndOr) {
        StringBuilder query = new StringBuilder();
        if (conditions.isEmpty())
            return "";
        query.append(" WHERE ");
        Map.Entry lastEntry = conditions.lastEntry();
        for (Map.Entry<String, String> entry: conditions.entrySet()) {
            query.append(inQuotes(entry.getKey())).append(" = ").append(entry.getValue());
            if (!lastEntry.equals(entry)){
                query.append(" "+AndOr+" ");
            }
        }
        return query.toString();
    }
    final String getJoin(){
        StringBuilder query = new StringBuilder();
        if (joins.isEmpty())
            return "";
        for (Join join: joins) {
            query.append(join.toString());
        }
        return query.toString();
    }

    public abstract String getInsertQuery();
    public abstract String getCreateTableQuery();
    public abstract String getUpdateQuery();

    public abstract String getPrimaryKey();
    public abstract String getTableName();
    public String getTableNameFix(){
        return inQuotes(getTableName());
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
