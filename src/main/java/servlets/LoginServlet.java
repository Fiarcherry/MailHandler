package servlets;

import database.controllers.DBHandler;
import database.models.*;
import database.query.Selector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        try (PrintWriter out = resp.getWriter()) {
            switch (action == null ? "login" : action) {
                case "registration":
                    if (session.getAttribute("name") == null)
                        break;
                    else
                        req.getRequestDispatcher("/Views/Registration.html").forward(req, resp);
                    break;
                case "logout":
                    if (session.getAttribute("name") != null) {
                        req.getSession().removeAttribute("login");
                        req.getSession().removeAttribute("name");
                    }
                    break;
                case "login":
                default:
                    resp.setContentType("text/html;charset=utf-8");
                    req.getRequestDispatcher("/Views/Login.html").forward(req, resp);
                    break;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            String action = req.getParameter("action");

            DBHandler db = DBHandler.getInstance();
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            HttpSession session = req.getSession();

            switch(action){
                case "registrationResult":
                    if (login.length() > 0 && password.length() > 0) {
                        if (session.getAttribute("name") != null) {
                            UserM user = new UserM(login, password);
                            db.insert(user);
                            session.setAttribute("login", user.getLogin());
                            session.setAttribute("name", user.getName());
                            out.write(req.getContextPath() + "/home");
                        } else {
                            resp.setContentType("text/html;charset=utf-8");
                            out.write("You need to authorize");
                        }
                    }
                    break;
                case "loginResult":
                    if (login.length() > 0 && password.length() > 0) {
                        UserM user = db.getObject(new UserM(login, password)
                                .addCondition(UserM.LOGIN_DEF, Model.toText(login))
                                .addCondition(UserM.PASSWORD_DEF, Model.toText(password)))  ;
                        if (user != null) {
                            session.setAttribute("login", user.getLogin());
                            session.setAttribute("name", user.getName());
                            out.write(req.getContextPath() + "/home");
                        } else {
                            resp.setContentType("text/html;charset=utf-8");
                            out.write("Invalid login or password");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
