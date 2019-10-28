package servlets;

import controllers.LoginController;
import database.controllers.DBHandler;
import database.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
                        resp.sendRedirect("http://localhost:8080/MailHandler/");
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
            resp.setContentType("text/html;charset=utf-8");

            DBHandler db = DBHandler.getInstance();
            LoginController loginController = new LoginController(req.getSession(), req);

            switch(action){
                case "registrationResult":
                    out.write(loginController.register(new UserM(req.getParameter("nick"), req.getParameter("login"), req.getParameter("password"), req.getParameter("email"))));
                    break;
                case "loginResult":
                    out.write(loginController.login(req.getParameter("login"), req.getParameter("password")));
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
