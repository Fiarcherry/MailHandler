package servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.getRequestDispatcher("/Views/Home.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try (PrintWriter out = resp.getWriter()) {
            switch (action == null ? "create" : action) {
                case "json":
                    resp.setContentType("application/json;charset=utf-8");
                    HttpSession session = req.getSession();
                    //String login = session.getAttribute("login").toString();
                    String name = session.getAttribute("name") == null ? "NULLLOGIN" : session.getAttribute("name").toString();

                    System.out.println(name);
                    out.println(new Gson().toJson(new String[]{name}));
                    break;
                default:
                    resp.setContentType("text/html; charset=UTF-8");
                    req.getRequestDispatcher("/Views/Home.html").forward(req, resp);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
