package servlets;

import database.controllers.DBHandler;
import database.models.PaymentM;
import mail.controllers.MessageHandler;
import mail.models.EMessage;
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

        try (PrintWriter out = resp.getWriter()) {
            switch (action == null ? "create" : action) {
                case "json":
                    resp.setContentType("application/json;charset=utf-8");
                    out.println(new Gson().toJson(DBHandler.getInstance().getAll(new PaymentM())));
                    break;
                case "show":
                    resp.setContentType("text/jsp;charset=utf-8");
                    req.getRequestDispatcher("/Views/AllPayments.jsp").forward(req, resp);
                    break;
                case "read":
                    MessageHandler messageHandler = new MessageHandler();
                    messageHandler.readEmail();
                    resp.sendRedirect("http://localhost:8080/MailHandler/message?action=show");
                    break;
                case "create":
                default:
                    resp.setContentType("text/jsp;charset=utf-8");
                    req.getRequestDispatcher("/Views/NewMessage.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException | MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            MessageHandler messageHandler = new MessageHandler();
            String action = req.getParameter("action");
            if ("send".equalsIgnoreCase(action)) {
                EMessage message = new EMessage(req.getParameter("to"), "Payment", req.getParameter("message"));
                out.println(messageHandler.sendMessage(message));
            } else if ("show".equalsIgnoreCase(action) || "json".equalsIgnoreCase(action)) {
                DBHandler db = DBHandler.getInstance();
                PaymentM[] payments = PaymentM.getPayments(db.parseUni(req.getParameter("json")));
                PaymentM.updateChecked(payments);
                MessageHandler mh = new MessageHandler();
                //TODO Начать с этого mh.sendPayments(payments);
                out.println(new Gson().toJson(db.getAll(new PaymentM())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
