package common;

import com.mpt.databasehandler.*;
import database.models.ClientM;
import database.models.ErrorM;
import database.models.OrderM;
import database.models.PaymentM;

import java.util.List;
import java.util.Map;

public class Queries {

    public static List<Map<String, String>> getPaymentsJson(){
        return DataBaseHandler.getInstance().get(new PaymentM()
                .addJoin(JoinType.INNER, OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.TABLE_NAME, PaymentM.ID_ORDER_DEF)
                .addJoin(JoinType.INNER, ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.TABLE_NAME, OrderM.ID_CLIENT_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.FIRST_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.SECOND_NAME_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.EMAIL_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.DATE_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.AMOUNT_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.IS_PROCESSED_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.ID_DEF, "paymentID"));
    }

    public static List<ErrorM> getJsonErrors(){
        return DataBaseHandler.getInstance().getObjects(new ErrorM());
    }

    public static List<OrderM> getJsonOrders(){
        return DataBaseHandler.getInstance().getObjects(new OrderM());
    }

    public static List<ClientM> getJsonClients(){
        return DataBaseHandler.getInstance().getObjects(new ClientM());
    }

    public static Map<String, String> getSendPayments(String paymentId){
        System.out.println("ID: "+paymentId);
        return DataBaseHandler.getInstance().getFirst(new PaymentM()
                .addJoin(JoinType.INNER, OrderM.TABLE_NAME, OrderM.ID_DEF, PaymentM.TABLE_NAME, PaymentM.ID_ORDER_DEF)
                .addJoin(JoinType.INNER, ClientM.TABLE_NAME, ClientM.ID_DEF, OrderM.TABLE_NAME, OrderM.ID_CLIENT_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.ID_DEF)
                .addSelector(ClientM.TABLE_NAME, ClientM.EMAIL_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.DATE_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.AMOUNT_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.BANK_COMMISSION_DEF)
                .addSelector(PaymentM.TABLE_NAME, PaymentM.ID_DEF, "paymentID")
                .addCondition(PaymentM.TABLE_NAME, PaymentM.ID_DEF, paymentId, true));

    }
}
