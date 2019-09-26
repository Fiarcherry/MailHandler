package servlets;

import controllers.MessageHandler;
import models.EMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        String action = req.getParameter("action");

        switch (action == null? "create": action){
            case "create":
            default:
                req.getRequestDispatcher("/views/NewMessage.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");

        if ("send".equalsIgnoreCase(action)){
            EMessage message = new EMessage(req.getParameter("to"), req.getParameter("message"));
            MessageHandler messageHandler = new MessageHandler();
            out.println(messageHandler.sendMessage(message));
        }
    }
}
