package servlets;

import database.controllers.DBHandler;
import database.models.ClientM;
import database.models.ErrorM;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        try (PrintWriter out = resp.getWriter()) {
            MessageHandler messageHandler = new MessageHandler();
            switch (action == null ? "create" : action) {
                case "json":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        out.println(getPaymentsJson());
                    }
                    break;
                case "jsonError":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        System.out.println(getJsonErrors());
                        out.println(getJsonErrors());
                    }
                    break;
                case "jsonOrder":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        System.out.println(getJsonOrders());
                        out.println(getJsonOrders());
                    }
                    break;
                case "jsonClient":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        System.out.println(getJsonClients());
                        out.println(getJsonClients());
                    }
                    break;
                case "show":
                    req.getRequestDispatcher("/Views/AllPayments.html").forward(req, resp);
                    break;
                case "readNew":
                    messageHandler.readEmail("new");
                    resp.sendRedirect("http://localhost:8080/MailHandler/message?action=show");
                    break;
                case "readAll":
                    //messageHandler.readEmail("all");
                    req.getRequestDispatcher("/Views/ReadResult.html").forward(req, resp);
                    break;
                case "readResult":
                    resp.setContentType("application/json;charset=utf-8");
                    //out.write(new Gson().toJson(messageHandler.readEmail("all")));
                    break;
                case "showErrors":
                    req.getRequestDispatcher("/Views/Errors.html").forward(req, resp);
                    break;
                case "showOrders":
                    req.getRequestDispatcher("/Views/Orders.html").forward(req, resp);
                    break;
                case "showClients":
                    req.getRequestDispatcher("/Views/Clients.html").forward(req, resp);
                    break;
                case "create":
                default:
                    req.getRequestDispatcher("/Views/NewMessage.html").forward(req, resp);
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
                MessageHandler mh = new MessageHandler();
                mh.sendPayments(getSendPayments());
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

    private String getJsonErrors() throws SQLException{
        return new Gson().toJson(DBHandler.getInstance().getObjects(new ErrorM()));
    }

    private String getJsonOrders() throws SQLException{
        return new Gson().toJson(DBHandler.getInstance().getObjects(new OrderM()));
    }

    private String getJsonClients() throws SQLException{
        return new Gson().toJson(DBHandler.getInstance().getObjects(new ClientM()));
    }

    private List<Map<String, String>> getSendPayments() throws SQLException {
        return DBHandler.getInstance().get(new PaymentM()
                .addJoin(OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.TABLE_NAME, PaymentM.ID_ORDER_DEF)
                .addJoin(ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.TABLE_NAME, OrderM.ID_CLIENT_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.ID_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.EMAIL_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.DATE_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.AMOUNT_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.BANK_COMMISSION_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.ID_DEF, "paymentID"));

    }
}
