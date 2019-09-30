package DataBase.Models;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Operation")
public class PaymentM {

    public static final String TABLE_NAME = "payments";

    public static final String UNI_DEF = "uni";
    public static final String NUMBER_DEF = "number";
    public static final String DATE_OPERATION_DEF = "dateoperation";
    public static final String ACCOUNT_DEF = "Account";
    public static final String AMOUNT_DEF = "Amount";
    public static final String COMMISSION_DEF = "Commission";

    @XmlAttribute(name = "Uni")
    private String uni;
    @XmlAttribute(name = "Number")
    private Integer number;
    @XmlAttribute(name = "DateOperation")
    private String dateOperation;
    @XmlAttribute(name = "Account")
    private Integer account;
    @XmlAttribute(name = "Amount")
    private Float amount;
    @XmlAttribute(name = "Commission")
    private Float commission;

    public PaymentM(String uni, Integer number, String dateOperation, Integer account, Float amount, Float commission) {
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

    public Integer getNumber() {
        return number;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public Integer getAccount() {
        return account;
    }

    public Float getAmount() {
        return amount;
    }

    public Float getCommission() {
        return commission;
    }
}
