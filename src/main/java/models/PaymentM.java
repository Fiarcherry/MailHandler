package models;

public class PaymentM {

    public static final String TABLE_NAME = "'payments'";

    public static final String UNI_DEF = "'Uni'";
    public static final String NUMBER_DEF = "'Number'";
    public static final String DATE_OPERATION_DEF = "'DateOperation'";
    public static final String ACCOUNT_DEF = "'Account'";
    public static final String AMOUNT_DEF = "'Amount'";
    public static final String COMMISSION_DEF = "'Commission'";

    private String uri;
    private Integer number;
    private String dateOperation;
    private Integer account;
    private Float amount;
    private Float commission;

    public PaymentM(String uri, Integer number, String dateOperation, Integer account, Float amount, Float commission) {
        this.uri = uri;
        this.number = number;
        this.dateOperation = dateOperation;
        this.account = account;
        this.amount = amount;
        this.commission = commission;
    }

    public String getUri() {
        return uri;
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
