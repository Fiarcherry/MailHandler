package Mail.Models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMessage {

    private String to;
    private String messageText;
    private String subject;

    public EMessage(String to, String subject, String messageText) {
        this.to = to;
        this.messageText = messageText;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSubject() {
        return subject;
    }
}
