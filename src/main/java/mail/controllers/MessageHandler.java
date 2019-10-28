package mail.controllers;

import database.controllers.DBHandler;
import database.controllers.XMLParser;
import database.models.ClientM;
import database.models.ErrorM;
import database.models.OrderM;
import database.models.PaymentM;
import mail.models.EMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageHandler {

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

    public void sendPayments(List<Map<String, String>> payments) throws SQLException{
        for (Map<String, String> paymentData: payments){
            StringBuilder text = new StringBuilder();
            text.append("Сообщаем вам об удачном платеже.")
                    .append("\nНомер аккаунта: ").append(paymentData.get(ClientM.ID_DEF))
                    .append("\nДата проведения операции: ").append(paymentData.get(PaymentM.DATE_DEF))
                    .append("\nСумма платежа: ").append(paymentData.get(PaymentM.AMOUNT_DEF))
                    .append("\nВ том числе комиссия: ").append(paymentData.get(PaymentM.BANK_COMMISSION_DEF));
            sendMessage(new EMessage(paymentData.get(ClientM.EMAIL_DEF), "Payments", text.toString()));
            PaymentM.updateChecked(PaymentM.getPayment(paymentData.get("paymentID")));
        }
    }

    public void readEmail(String flag) throws MessagingException, SQLException, IOException {
        Folder inbox = MailConnect.getInstance().getStore().getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        System.out.println("Messages count: " + inbox.getMessageCount());
        if (inbox.getMessageCount() == 0)
            return;

        Message[] messages = inbox.getMessages();

        for (int i = 0; i < inbox.getMessageCount(); i++){
            Object content = messages[i].getContent();

            System.out.println("Message number: " + i);
            String subj = messages[i].getSubject();
            if(subj.equals("PRSS")){
                if (flag == "new"){
                    if (!messages[i].getFlags().contains(Flags.Flag.SEEN)){
                        multipartCheck(content);
                        messages[i].setFlag(Flags.Flag.SEEN, true);
                    } else {
                        System.out.println("Message has been already seen");
                    }
                } else {
                    multipartCheck(content);
                }
            } else {
                System.out.println("Wrong message theme");
                ErrorM.errorAdd("Wrong message theme");
            }
        }
        inbox.close();
    }
    private void multipartCheck(Object content) throws SQLException, IOException, MessagingException {
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
                        if (currentPayment == null){
                            System.out.println("Wrong XML-file structure");
                            ErrorM.errorAdd("Wrong XML-file structure");
                        } else {
                            OrderM order = DBHandler.getInstance().getObject(new OrderM().addCondition(OrderM.ID_DEF, currentPayment.getIdOrder(), true));
                            if (order != null) {
                                List<PaymentM> payments = DBHandler.getInstance().getObjects(new PaymentM());

                                boolean correctPayment = true;

                                for (PaymentM payment : payments) {
                                    if (payment.getId().equals(currentPayment.getId())){
                                        correctPayment = false;
                                        System.out.println("Payment already exists");
                                        ErrorM.errorAdd("Payment already exists");
                                    }
                                }

                                if (correctPayment){
                                    DBHandler.getInstance().insert(currentPayment);
                                    System.out.println("Message has been add to database");
                                }
                                savefile.delete();
                            } else {
                                System.out.println("Wrong order number");
                                ErrorM.errorAdd("Wrong order number");
                            }
                        }
                    }
                }
            }
        }
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
