package common;

import com.mpt.databasehandler.*;
import database.models.ClientM;
import database.models.ErrorM;
import database.models.OrderM;
import database.models.PaymentM;

import java.util.List;
import java.util.Map;

/**
 * Класс чтения данных для таблиц из базы данных
 */
public class Queries {

    /**
     * Чтение данных о платежах из базы данных
     * @return Платежи
     */
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

    /**
     * Чтение данных об ошибках из базы данных
     * @return Ошибки
     */
    public static List<ErrorM> getJsonErrors(){
        return DataBaseHandler.getInstance().getObjects(new ErrorM());
    }

    /**
     * Чтение данных о заказах из базы данных
     * @return Заказы
     */
    public static List<OrderM> getJsonOrders(){
        return DataBaseHandler.getInstance().getObjects(new OrderM());
    }

    /**
     * Чтение данных о клиентах из базы данных
     * @return Клиенты
     */
    public static List<ClientM> getJsonClients(){
        return DataBaseHandler.getInstance().getObjects(new ClientM());
    }

    /**
     * Получить платёж для отправления
     * @param paymentId Ключ платежа
     * @return Платёж
     */
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
