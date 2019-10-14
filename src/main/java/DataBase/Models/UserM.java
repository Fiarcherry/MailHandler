package DataBase.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserM extends Model{

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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isActive() {
        return active;
    }


    public void setId(Integer id){
        this.id = id;
    }



    public UserM() {
    }

    public UserM(Integer id) {
        this.id = id;
    }

    public UserM(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserM(Integer id, String name, String login, String password, String email, Boolean active) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.active = active;
    }

    public UserM(String name, String login, String password, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.active = false;
    }

    public UserM(ResultSet resultSet)  throws SQLException {
        this(resultSet.getInt(ID_DEF),
                resultSet.getString(NAME_DEF),
                resultSet.getString(LOGIN_DEF),
                resultSet.getString(PASSWORD_DEF),
                resultSet.getString(EMAIL_DEF),
                resultSet.getBoolean(ACTIVE_DEF));
    }


    @Override
    public String getPrimaryKey() {
        return id.toString();
    }

    @Override
    public  String getCreateTableQuery(){
        return String.format("CREATE TABLE if not exists '%s' ('%s' INTEGER PRIMARY KEY, '%s' TEXT, '%s' TEXT, '%s' TEXT, '%s' TEXT, '%s' INTEGER DEFAULT 0);",
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
        return String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",
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
        return String.format("update %s set %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = %s where %s = %s",
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
    public String getSelectAllQuery() {
        return  "SELECT * FROM " + TABLE_NAME;
    }

    @Override
    public String getSelectFirstQuery() {
        return String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, ID_DEF, getPrimaryKey());
    }

    public String getSelectLoginQuery(){
        return "SELECT * FROM "+TABLE_NAME+" WHERE "+LOGIN_DEF+" = \""+login+"\" AND "+PASSWORD_DEF+" = \""+password+"\"";
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



}
