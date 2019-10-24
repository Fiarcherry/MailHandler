package servlets;

import database.controllers.DBHandler;
import database.models.ClientM;
import database.models.OrderM;
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
                    DBHandler db = DBHandler.getInstance();
                    String json = getPaymentsJson();
                    System.out.println(json);
                    out.println(json);
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
                //mh.sendPayments(payments);
                String json = getPaymentsJson();
                System.out.println(json);
                out.println(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getPaymentsJson() throws SQLException{
        return new Gson().toJson(DBHandler.getInstance().get(new PaymentM()
                .addJoin(OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.TABLE_NAME, PaymentM.ID_ORDER_DEF)
                .addJoin(ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.TABLE_NAME, OrderM.ID_CLIENT_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.FIRST_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.SECOND_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.EMAIL_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.DATE_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.AMOUNT_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.IS_PROCESSED_DEF)));
    }
}
