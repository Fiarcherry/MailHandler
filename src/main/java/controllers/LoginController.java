package controllers;

import com.mpt.databasehandler.DataBaseHandler;
import com.mpt.databasehandler.Model;
import common.Config;
import database.models.UserM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Контроллер авторизации и регистрации
 */
public class LoginController {
    private HttpSession session;    //Сессия http
    private DataBaseHandler db;     //Обработчик базы данных
    private HttpServletRequest req; //Запрос с сервлета

    /**
     * Конструктор контроллера
     *
     * @param session Сессия
     * @param req Запрос
     */
    public LoginController(HttpSession session, HttpServletRequest req) {
        this.session = session;
        this.db = DataBaseHandler.getInstance();
        this.req = req;
    }

    /**
     * Процесс авторизации
     *
     * @param login Логин
     * @param password Пароль
     * @return Ошибка или запрос на переход на главную страницу
     */
    public String login(String login, String password) {
        if (login.length() > 0 && password.length() > 0) {
            UserM user = (UserM) db.getObject(new UserM(login, password)
                    .addCondition(UserM.LOGIN_DEF, Model.toText(login))
                    .addCondition(UserM.PASSWORD_DEF, Model.toText(password)));
            System.out.println(user);
            if (user != null) {
                session.setAttribute("login", user.getLogin());
                session.setAttribute("name", user.getName());
                session.setAttribute("email", user.getEmail());
                return req.getContextPath() + "/home";
            } else {
                return "Invalid login or password";
            }
        } else {
            return "One or more input fields empty.";
        }
    }

    /**
     * Процесс регистрации
     *
     * @param user
     * @return Ошибка или запрос на переход на главную страницу
     * @throws SQLException
     */
    public String register(UserM user) throws SQLException {
        if (user.getLogin().length() == 0 || user.getPassword().length() == 0 || user.getName().length() == 0 || user.getEmail().length() == 0)
            return "One or more input fields empty.";
        else if (session.getAttribute("name") == null)
            return "You need to authorize";
        else if (user.getLogin().equals(db.getFirst(new UserM().addSelector(UserM.TABLE_NAME, UserM.LOGIN_DEF)).get(UserM.LOGIN_DEF)))
            return "This login is already in use";
        else {
            db.insert(user);
            session.setAttribute("login", user.getLogin());
            session.setAttribute("name", user.getName());
            session.setAttribute("email", user.getEmail());
            return req.getContextPath() + "/home";
        }

    }

}
