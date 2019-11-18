package database.models;

import com.mpt.databasehandler.JoinType;
import com.mpt.databasehandler.Model;
import com.mpt.databasehandler.Selector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель для пользователей
 */
public class UserM extends Model {

    public static final String TABLE_NAME = "User";

    public static final String ID_DEF = "id";
    public static final String NAME_DEF = "name";
    public static final String LOGIN_DEF = "login";
    public static final String PASSWORD_DEF = "password";
    public static final String EMAIL_DEF = "email";
    public static final String ACTIVE_DEF = "isactive";

    private Integer id;
    private String name;
    private String login;
    private String password;
    private String email;
    private Boolean active;

    /**
     * Чтение ключа
     * @return Ключ
     */
    public Integer getId() {
        return id;
    }

    /**
     * Чтение имени
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Чтение логина
     * @return Логин
     */
    public String getLogin() {
        return login;
    }

    /**
     * Чтение пароля
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Чтение почты
     * @return Почта
     */
    public String getEmail() {
        return email;
    }

    /**
     * Чтение статуса
     * @return Текущий ли пользователь
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Запись ключа
     * @param id Ключ
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Конструктор модели пользователя
     */
    public UserM() {
    }

    /**
     * Конструктор модели пользователя
     * @param id Ключ
     */
    public UserM(Integer id) {
        this.id = id;
    }

    /**
     * Конструктор модели пользователя
     * @param login Логин
     * @param password Пароль
     */
    public UserM(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Конструктор модели пользователя
     * @param id КЛюч
     * @param name Имя
     * @param login Логин
     * @param password Пароль
     * @param email Почта
     * @param active Статус
     */
    public UserM(Integer id, String name, String login, String password, String email, Boolean active) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.active = active;
    }

    /**
     * Конструктор модели пользователя
     * @param name Имя
     * @param login Логин
     * @param password Пароль
     * @param email Почта
     */
    public UserM(String name, String login, String password, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.active = false;
    }

    /**
     * Конструктор модели пользователя
     * @param resultSet Данные из базы данных
     * @throws SQLException
     */
    public UserM(ResultSet resultSet)  throws SQLException {
        this(resultSet.getInt(ID_DEF),
                resultSet.getString(NAME_DEF),
                resultSet.getString(LOGIN_DEF),
                resultSet.getString(PASSWORD_DEF),
                resultSet.getString(EMAIL_DEF),
                resultSet.getBoolean(ACTIVE_DEF));
    }

    @Override
    public String getCreateTableQuery(){
        return String.format("CREATE TABLE if not exists `%s` (" +
                        "`%s` INTEGER PRIMARY KEY, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` TEXT, " +
                        "`%s` INTEGER DEFAULT 0);",
                TABLE_NAME,
                ID_DEF,
                NAME_DEF,
                LOGIN_DEF,
                PASSWORD_DEF,
                EMAIL_DEF,
                ACTIVE_DEF);
    }
    @Override
    public String getInsertQuery() {
        return String.format("INSERT INTO `%s` (`%s`, `%s`, `%s`, `%s`) " +
                        "VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",
                TABLE_NAME,
                NAME_DEF,
                LOGIN_DEF,
                PASSWORD_DEF,
                EMAIL_DEF,
                this.name,
                this.login,
                this.password,
                this.email);
    }
    @Override
    public String getUpdateQuery() {
        return String.format("update `%s` set " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = \"%s\", " +
                        "`%s` = %s " +
                        "where `%s` = %s",
                TABLE_NAME,
                NAME_DEF,
                this.getName(),
                LOGIN_DEF,
                this.getLogin(),
                PASSWORD_DEF,
                this.getPassword(),
                EMAIL_DEF,
                this.getEmail(),
                ACTIVE_DEF,
                this.isActive()?1:0,
                ID_DEF,
                this.getId());
    }

    @Override
    public String getPrimaryKey() {
        return id.toString();
    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<UserM> getResultList(ResultSet resultSet) throws SQLException {
        List<UserM> rows = new ArrayList<>();
        while (resultSet.next()) {
            rows.add(new UserM(resultSet));
        }
        return rows;
    }
    @Override
    public UserM getResult(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(ID_DEF);
        name = resultSet.getString(NAME_DEF);
        login = resultSet.getString(LOGIN_DEF);
        password = resultSet.getString(PASSWORD_DEF);
        email = resultSet.getString(EMAIL_DEF);
        active = resultSet.getBoolean(ACTIVE_DEF);
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
