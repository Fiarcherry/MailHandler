package servlets;
import common.Queries;
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
                        out.println(new Gson().toJson(Queries.getPaymentsJson()));
                    }
                    break;
                case "jsonError":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        String jsonErrors = new Gson().toJson(Queries.getJsonErrors());
                        System.out.println(jsonErrors);
                        out.println(jsonErrors);
                    }
                    break;
                case "jsonOrder":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        String jsonOrders = new Gson().toJson(Queries.getJsonOrders());
                        System.out.println(jsonOrders);
                        out.println(jsonOrders);
                    }
                    break;
                case "jsonClient":
                    if (session.getAttribute("login") != null) {
                        resp.setContentType("application/json;charset=utf-8");
                        String jsonClients = new Gson().toJson(Queries.getJsonClients());
                        System.out.println(jsonClients);
                        out.println(jsonClients);
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
                mh.sendPayments(Queries.getSendPayments());
                String jsonPayments = new Gson().toJson(Queries.getPaymentsJson());
                System.out.println(jsonPayments);
                out.println(jsonPayments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
