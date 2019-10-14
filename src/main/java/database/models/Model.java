package database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class Model <T extends Model>{
    private NavigableMap<String, String> conditions = new TreeMap<>();

    public T removeCondition(String key){
        conditions.remove(key);
        return null;
    }
    public T addCondition(String key, String value){
        conditions.put(key, value);
        return null;
    }
    public T removeAllConditions(){
        conditions.clear();
        return null;
    }
    public static String toText(String value){
        return "\""+value+"\"";
    }

    public String getWhere(String AndOr) {
        StringBuilder query = new StringBuilder();
        if (conditions.isEmpty())
            return "";
        query.append(" WHERE ");
        Map.Entry lastEntry = conditions.lastEntry();
        for (Map.Entry<String, String> entry: conditions.entrySet()) {
            query.append(entry.getKey()).append(" = ").append(entry.getValue());
            if (!lastEntry.equals(entry)){
                query.append(" "+AndOr+" ");
            }
        }
        return query.toString();
    }

    public abstract String getInsertQuery();
    public abstract String getCreateTableQuery();
    public abstract String getUpdateQuery();
    public abstract String getSelectAllQuery();
    public abstract String getSelectFirstQuery();

    public abstract String getPrimaryKey();

    public abstract List<T> getResultList(ResultSet resultSet) throws SQLException;
    public abstract T getResult(ResultSet resultSet) throws SQLException;

}
