package servlets;

import com.google.gson.reflect.TypeToken;
import com.mpt.databasehandler.DataBaseHandler;
import common.Config;
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
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MessageServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DataBaseHandler.getInstance().initialize(Config.config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

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
                case "readMail":
                    req.getRequestDispatcher("/Views/ReadResult.html").forward(req, resp);

                    break;
                case "readResultNew":
                    resp.setContentType("application/json;charset=utf-8");
                    out.write(new Gson().toJson(messageHandler.readEmail("new")));
                    break;
                case "readResultAll":
                    resp.setContentType("application/json;charset=utf-8");
                    out.write(new Gson().toJson(messageHandler.readEmail("all")));
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

                String json = req.getParameter("json");

                Type type = new TypeToken<List<String>>() {}.getType();
                List<String> paymentsID = new Gson().fromJson(json, type);
                System.out.println("LIST: "+paymentsID);

                MessageHandler mh = new MessageHandler();
                List<Map<String, String>> payments = new ArrayList<>();
                Stream<String> paymentStream = paymentsID.stream();
                paymentStream.forEach(id -> {
                    Map<String, String> payment = Queries.getSendPayments(id);
                    System.out.println("PAYMENT: "+payment);
                    payments.add(payment);
                });

                System.out.println("PAYMENTS: " + payments);
                mh.sendPayments(payments);
                String jsonPayments = new Gson().toJson(Queries.getPaymentsJson());
                System.out.println(jsonPayments);
                out.println(jsonPayments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
