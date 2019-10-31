package app;

import common.Queries;
import mail.controllers.Log;
import mail.controllers.MessageHandler;

import java.sql.SQLException;

public class SenderBot {
    public static void main(String[] args) {
        try {
            MessageHandler mh = new MessageHandler();
            String[] logs = mh.sendPayments(Queries.getSendPayments());
            for (String log:logs) {
                Log.write(log);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
