package controllers;

import database.controllers.DBHandler;
import database.models.Model;
import database.models.UserM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class LoginController {
    private HttpSession session;
    private DBHandler db;
    private HttpServletRequest req;

    public LoginController(HttpSession session, HttpServletRequest req) throws SQLException{
        this.session = session;
        this.db = DBHandler.getInstance();
        this.req = req;
    }

    public String login(String login, String password) throws SQLException{
        if (login.length() > 0 && password.length() > 0) {
            UserM user = db.getObject(new UserM(login, password)
                    .addCondition(UserM.LOGIN_DEF, Model.toText(login))
                    .addCondition(UserM.PASSWORD_DEF, Model.toText(password)))  ;
            if (user != null) {
                session.setAttribute("login", user.getLogin());
                session.setAttribute("name", user.getName());
                session.setAttribute("email", user.getEmail());
                return  req.getContextPath() + "/home";
            } else {
                return"Invalid login or password";
            }
        }
        else{
            return "One or more input fields empty.";
        }
    }
    public String register(UserM user) throws SQLException {
        if (user.getLogin().length() == 0 || user.getPassword().length() == 0 || user.getName().length() == 0 || user.getEmail().length() == 0)
            return "One or more input fields empty.";
        else
            if (session.getAttribute("name") == null)
                return "You need to authorize";
            else
                if (user.getLogin().equals(db.getFirst(new UserM().addSelector(UserM.TABLE_NAME, UserM.LOGIN_DEF)).get(UserM.LOGIN_DEF)))
                    return "This login is already in use";
                else{
                db.insert(user);
                session.setAttribute("login", user.getLogin());
                session.setAttribute("name", user.getName());
                session.setAttribute("email", user.getEmail());
                return req.getContextPath() + "/home";
                }

    }

}
