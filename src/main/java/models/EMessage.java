package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMessage {

    private String to;
    private String messageText;

    public EMessage(String to,String messageText) {
        this.to = to;
        this.messageText = messageText;
    }

    public String getTo() {
        return to;
    }

    public String getMessageText() {
        return messageText;
    }
}
