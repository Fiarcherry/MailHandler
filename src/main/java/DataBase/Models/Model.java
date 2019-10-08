package DataBase.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Model <T extends Model>{
    String getInsertQuery();
    String getCreateTableQuery();
    String getUpdateQuery();
    String getSelectAllQuery();
    String getSelectFirstQuery();

    String getPrimaryKey();

    List<T> getResultList(ResultSet resultSet) throws SQLException;
    T getResult(ResultSet resultSet) throws SQLException;

}
