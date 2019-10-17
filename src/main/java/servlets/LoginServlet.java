package servlets;

import database.controllers.DBHandler;
import database.models.Model;
import database.models.UserM;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try (PrintWriter out = resp.getWriter()) {
            switch (action == null ? "login" : action) {
                case "registration":
                    break;
                case "loginResult":
                    break;
                case "login":
                default:
                    resp.setContentType("text/html;charset=utf-8");
                    req.getRequestDispatcher("/Views/Login.html").forward(req, resp);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            String action = req.getParameter("action");

            if ("loginResult".equalsIgnoreCase(action)){
                System.out.println("loginResult");
                DBHandler db = DBHandler.getInstance();
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                if (login.length() > 0 && password.length() > 0){
                    UserM user = db.getByCondition(new UserM(login, password)
                            .addCondition(UserM.LOGIN_DEF, Model.toText(login))
                            .addCondition(UserM.PASSWORD_DEF, Model.toText(password)));
                    if (user!= null){
                        resp.sendRedirect(req.getContextPath()+"/");
                    }
                    else{
                        resp.setContentType("application/javascript;charset=utf-8");
                        resp.sendRedirect(req.getContextPath()+"/auth?action=login");
                        out.println("Invalid login or password");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
