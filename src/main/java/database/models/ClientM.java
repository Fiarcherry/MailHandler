package database.models;

import com.mpt.databasehandler.JoinType;
import com.mpt.databasehandler.Model;
import com.mpt.databasehandler.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель для клиентов
 */
public class ClientM extends Model {

    public static final String TABLE_NAME = "Client";

    public static final String ID_DEF = "Id";
    public static final String FIRST_NAME_DEF = "FirstName";
    public static final String SECOND_NAME_DEF = "SecondName";
    public static final String EMAIL_DEF = "Email";

    private String id;
    private String firstName;
    private String secondName;
    private String email;

    /**
     * Чтение ключа
     *
     * @return Ключ
     */
    public String getId() {
        return id;
    }

    /**
     * Чтение имени
     *
     * @return Имя
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Чтение фамилиии
     *
     * @return Фамилия
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Чтение почты
     *
     * @return Почта
     */
    public String getEmail() {
        return email;
    }


    /**
     * Конструктор модели клиента
     */
    public ClientM() {
    }

    /**
     * Конструктор модели клиента
     *
     * @param id Ключ
     */
    public ClientM(String id) {
        this.id = id;
    }

    /**
     * Конструктор модели клиента
     *
     * @param id Ключ
     * @param firstName Имя
     * @param secondName Фамилия
     * @param email Почта
     */
    public ClientM(String id, String firstName, String secondName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }

    /**
     * Конструктор модели клиента
     * @param resultSet Данные из базы данных
     * @throws SQLException
     */
    public ClientM(ResultSet resultSet) throws SQLException {
        this(resultSet.getString(ClientM.ID_DEF),
                resultSet.getString(ClientM.FIRST_NAME_DEF),
                resultSet.getString(ClientM.SECOND_NAME_DEF),
                resultSet.getString(ClientM.EMAIL_DEF));
    }

    @Override
    public String getInsertQuery() {
        String query = String.format("INSERT INTO `%s` (`%s`, `%s`, `%s`, `%s`) " +
                        "VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",
                ClientM.TABLE_NAME,
                ClientM.ID_DEF,
                ClientM.FIRST_NAME_DEF,
                ClientM.SECOND_NAME_DEF,
                ClientM.EMAIL_DEF,
                this.getId(),
                this.getFirstName(),
                this.getSecondName(),
                this.getEmail());

        System.out.println(query);
        return query;
    }

    @Override
    public String getCreateTableQuery() {
        String query = String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` TEXT PRIMARY KEY, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT DEFAULT \"testClient@bg.mail\");",
                ClientM.TABLE_NAME,
                ClientM.ID_DEF,
                ClientM.FIRST_NAME_DEF,
                ClientM.SECOND_NAME_DEF,
                ClientM.EMAIL_DEF);

        System.out.println(query);
        return query;
    }

    @Override
    public String getUpdateQuery() {
        String query = String.format("update `%s` set " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\" " +
                        "where `%s` = \"%s\"",
                ClientM.TABLE_NAME,
                ClientM.FIRST_NAME_DEF,
                this.getFirstName(),
                ClientM.SECOND_NAME_DEF,
                this.getSecondName(),
                ClientM.EMAIL_DEF,
                this.getEmail(),
                ClientM.ID_DEF,
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
    public List<ClientM> getResultList(ResultSet resultSet) throws SQLException {
        List<ClientM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new ClientM(resultSet));
        }
        return rows;
    }

    @Override
    public ClientM getResult(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString(ClientM.ID_DEF);
        this.firstName = resultSet.getString(ClientM.FIRST_NAME_DEF);
        this.secondName = resultSet.getString(ClientM.SECOND_NAME_DEF);
        this.email = resultSet.getString(ClientM.EMAIL_DEF);
        return this;
    }

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
