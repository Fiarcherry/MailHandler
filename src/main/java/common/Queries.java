package common;

import com.google.gson.Gson;
import database.controllers.DBHandler;
import database.models.ClientM;
import database.models.ErrorM;
import database.models.OrderM;
import database.models.PaymentM;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Queries {

    public static List<Map<String, String>> getPaymentsJson() throws SQLException {
        return DBHandler.getInstance().get(new PaymentM()
                .addJoin(OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.TABLE_NAME, PaymentM.ID_ORDER_DEF)
                .addJoin(ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.TABLE_NAME, OrderM.ID_CLIENT_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.FIRST_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.SECOND_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.EMAIL_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.DATE_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.AMOUNT_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.IS_PROCESSED_DEF));
    }

    public static List<ErrorM> getJsonErrors() throws SQLException{
        return DBHandler.getInstance().getObjects(new ErrorM());
    }

    public static List<OrderM> getJsonOrders() throws SQLException{
        return DBHandler.getInstance().getObjects(new OrderM());
    }

    public static List<ClientM> getJsonClients() throws SQLException{
        return DBHandler.getInstance().getObjects(new ClientM());
    }

    public static List<Map<String, String>> getSendPayments() throws SQLException {
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
