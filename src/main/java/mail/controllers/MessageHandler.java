package mail.controllers;

import database.controllers.DBHandler;
import database.controllers.XMLParser;
import database.models.PaymentM;
import mail.models.EMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class MessageHandler {

    public static int NEW_MESSAGES_COUNT;

    public String sendMessage(EMessage messageData){

        try {
            MimeMessage message = new MimeMessage(MailConnect.getInstance().getSessionSMTP());
            message.setText(messageData.getMessageText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageData.getTo()));
            message.setSubject(messageData.getSubject());

            Transport.send(message);
            return "Success.";
        }
        catch (MessagingException e){
            return e.getMessage();
        }
    }

    public void sendMessages(EMessage[] messages) throws MessagingException{
        Session session = MailConnect.getInstance().getSessionSMTP();
        for (EMessage eMessage : messages){
            MimeMessage message = new MimeMessage(session);
            message.setSubject(eMessage.getSubject());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(eMessage.getTo()));
            message.setText(eMessage.getMessageText());

            Transport.send(message);
        }
    }

//TODO Начать с этого
//    public void sendPayments(PaymentM[] payments) throws MessagingException{
//        EMessage[] messages = new EMessage[payments.length];
//        for (int i = 0; i < payments.length; i++){
//            StringBuilder text = new StringBuilder();
//            text.append("Сообщаем вам о удачном платеже.")
//                    .append("\nНомер аккаунта: ").append(payments[i].getAccount(payments[i].getId()))
//                    .append("\nДата проведения операции: ").append(payments[i].getDateOperation())
//                    .append("\nСумма платежа: ").append(payments[i].getAmount())
//                    .append("\nВ том числе комиссия: ").append(payments[i].getBankCommission());
//            messages[i] = new EMessage(payments[i].getEmail(), "Payments", text.toString());
//        }
//        sendMessages(messages);
//    }

    public void readEmail() throws MessagingException, SQLException, IOException {
        Folder inbox = MailConnect.getInstance().getStore().getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        System.out.println("Количество сообщений на почте: " + inbox.getMessageCount());
        if (inbox.getMessageCount() == 0)
            return;

        Message messages[] = inbox.getMessages();
        NEW_MESSAGES_COUNT = 0;

        for (int i = 0; i < inbox.getMessageCount(); i++){
            Object content = messages[i].getContent();

            System.out.println("Номер сообщения: " + i);

            if (!messages[i].getFlags().contains(Flags.Flag.SEEN)){
                messages[i].setFlag(Flags.Flag.SEEN, true);

                if (content instanceof Multipart){
                    Multipart mp = (Multipart) content;

                    for (int j = 0; j < mp.getCount(); j++){
                        Part part = mp.getBodyPart(j);

                        String disp = part.getDisposition();
                        if (disp != null) {
                            MimeBodyPart mbp = (MimeBodyPart)part;
                            if (!mbp.isMimeType("text/plain")) {
                                File savedir = new File(System.getProperty("user.home")+"/MessageTemp");
                                savedir.mkdirs();
                                File savefile = File.createTempFile("emailattach", ".atch", savedir );
                                try {
                                    int path = saveFile(savefile, part);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                PaymentM currentPayment = XMLParser.parsePayment(savefile);
                                boolean repeatPayment = false;
                                List<PaymentM> payments = DBHandler.getInstance().getObjects(new PaymentM());

                                for (PaymentM payment : payments) {
                                    if (payment.getId().equals(currentPayment.getId())){
                                        repeatPayment = true;
                                    }
                                }

                                if (!repeatPayment){
                                    DBHandler.getInstance().insert(currentPayment);
                                    NEW_MESSAGES_COUNT ++;
                                }
                                savefile.delete();
                            }
                        }
                    }
                }
            }
        }
        inbox.close();
    }

protected int saveFile(File saveFile, Part part) throws Exception {

    BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(saveFile) );

    byte[] buff = new byte[2048];
    InputStream is = part.getInputStream();
    int ret = 0, count = 0;
    while( (ret = is.read(buff)) > 0 ){
        bos.write(buff, 0, ret);
        count += ret;
    }
    bos.close();
    is.close();
    return count;
}
}
