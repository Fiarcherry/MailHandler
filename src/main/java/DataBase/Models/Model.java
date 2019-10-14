package DataBase.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Model <T extends Model>{
    public Map<String, String> conditions = new HashMap<>();
    
    //    public String getAndQuery(){
    //        StringBuilder query = new StringBuilder();
    //        if (conditions.isEmpty())
    //            return "";
    //        query.append(" WHERE ");
    //        conditions.forEach((k, v) -> {
    //            query.append()
    //        });
    //        return query;
    //    }

    public abstract String getInsertQuery();
    public abstract String getCreateTableQuery();
    public abstract String getUpdateQuery();
    public abstract String getSelectAllQuery();
    public abstract String getSelectFirstQuery();

    public abstract String getPrimaryKey();

    public abstract List<T> getResultList(ResultSet resultSet) throws SQLException;
    public abstract T getResult(ResultSet resultSet) throws SQLException;

}
