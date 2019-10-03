package Servlets;

import DataBase.Controllers.DBHandler;
import Mail.Controllers.MessageHandler;
import Mail.Models.EMessage;
import com.google.gson.Gson;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try (PrintWriter out = resp.getWriter()){
            switch (action == null ? "create" : action) {
                case "json":
                    resp.setContentType("application/json;charset=utf-8");
                    out.println(new Gson().toJson(DBHandler.getInstance().getAllPayments()));
                    break;
                case "show":
                    resp.setContentType("text/jsp;charset=utf-8");
                    req.getRequestDispatcher("/views/AllPayments.jsp").forward(req, resp);
                    break;
                case "read":
                    try {
                        MessageHandler messageHandler = new MessageHandler();

                        messageHandler.readEmail();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    break;
                case "create":
                default:
                    resp.setContentType("text/jsp;charset=utf-8");
                    req.getRequestDispatcher("/views/NewMessage.jsp").forward(req, resp);
                    break;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        MessageHandler messageHandler = new MessageHandler();

        String action = req.getParameter("action");

        if ("send".equalsIgnoreCase(action)){
            EMessage message = new EMessage(req.getParameter("to"), req.getParameter("message"));
            out.println(messageHandler.sendMessage(message));
        }
        else if ("read".equalsIgnoreCase(action)){
            try {
                messageHandler.readEmail();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }else if ("show".equalsIgnoreCase(action) || "json".equalsIgnoreCase(action)){
            try {
                DBHandler.getInstance().updateChecked(req.getParameter("json"));
                out.println(new Gson().toJson(DBHandler.getInstance().getAllPayments()));
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
