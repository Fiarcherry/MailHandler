package servlets;

import DataBase.Controllers.DBHandler;
import Mail.Controllers.MessageHandler;
import Mail.Models.EMessage;
import DataBase.Models.PaymentM;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");

        String action = req.getParameter("action");

        switch (action == null? "create": action){
            case "show":
                try {
                    List<PaymentM> payments = DBHandler.getInstance().getAllPayments();
                    String json = new Gson().toJson(payments);
                    resp.getWriter().write(json);
                    req.getRequestDispatcher("/AllPayments.jsp").forward(req, resp);
                }
                catch (SQLException e){
                    PrintWriter out = resp.getWriter();
                    out.println(e.toString());
                    e.printStackTrace();
                }
                break;
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

        MessageHandler messageHandler = new MessageHandler();

        String action = req.getParameter("action");

        if ("send".equalsIgnoreCase(action)){
            EMessage message = new EMessage(req.getParameter("to"), req.getParameter("message"));
            out.println(messageHandler.sendMessage(message));
        }
        else if ("read".equalsIgnoreCase(action)){
            messageHandler.readEmail();
        }
    }
}
