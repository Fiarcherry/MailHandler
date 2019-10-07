package DataBase.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Model {
    String getInsertQuery();
    String getCreateTableQuery();
    String getUpdateQuery();
    String getSelectAllQuery();
    String getSelectFirstQuery();

    String getPrimaryKey();

    List<Model> getResultList(ResultSet resultSet) throws SQLException;
    Model getResult(ResultSet resultSet) throws SQLException;

}
