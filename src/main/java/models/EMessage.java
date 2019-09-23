package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMessage {

    private String to;
    private String messageText;
    private String password;

    public EMessage(String to, String password, String messageText) {
        this.to = to;
        this.password = password;
        this.messageText = messageText;
    }

    public String getTo() {
        return to;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getPassword() {
        return password;
    }
}
