package Servlets;

import DataBase.Controllers.DBHandler;

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
        try (PrintWriter out = resp.getWriter()) {
            String action = req.getParameter("action");

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {
            String action = req.getParameter("action");

            if ("loginResult".equalsIgnoreCase(action)){
                DBHandler db = DBHandler.getInstance();
                if (db.getLoginResult(req.getParameter("login"), req.getParameter("password")) != null){
                    out.println("success");
                }
                else{
                    out.println("Invalid login or password");
                }
            }

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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
