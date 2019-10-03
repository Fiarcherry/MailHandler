package DataBase.Models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Operation")
public class PaymentM {

    public static final String TABLE_NAME = "Payments";

    public static final String UNI_DEF = "Uni";
    public static final String NUMBER_DEF = "Number";
    public static final String DATE_OPERATION_DEF = "DateOperation";
    public static final String ACCOUNT_DEF = "Account";
    public static final String AMOUNT_DEF = "Amount";
    public static final String COMMISSION_DEF = "Commission";
    public static final String EMAIL_DEF = "Email";
    public static final String IS_PROCESSED_DEF = "IsProcessed";

    @XmlAttribute(name = "Uni")
    private String uni;
    @XmlAttribute(name = "Number")
    private String number;
    @XmlAttribute(name = "DateOperation")
    private String dateOperation;
    @XmlAttribute(name = "Account")
    private String account;
    @XmlAttribute(name = "Amount")
    private Float amount;
    @XmlAttribute(name = "Commission")
    private Float commission;

    private String email;

    private Boolean isProcessed;

    public PaymentM() {
    }

    public PaymentM(String uni, String number, String dateOperation, String account, Float amount, Float commission, String email, Boolean isProcessed) {
        this.uni = uni;
        this.number = number;
        this.dateOperation = dateOperation;
        this.account = account;
        this.amount = amount;
        this.commission = commission;
        this.email = email;
        this.isProcessed = isProcessed;
    }

    public PaymentM(String uni, String number, String dateOperation, String account, Float amount, Float commission) {
        this.uni = uni;
        this.number = number;
        this.dateOperation = dateOperation;
        this.account = account;
        this.amount = amount;
        this.commission = commission;
    }

    public String getUni() {
        return uni;
    }

    public String getNumber() {
        return number;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public String getAccount() {
        return account;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getCommission() {
        return commission;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void switchProcessed(){
        this.isProcessed = !this.isProcessed;
    }

    @Override
    public String toString() {
        return uni+isProcessed.toString();
    }
}
