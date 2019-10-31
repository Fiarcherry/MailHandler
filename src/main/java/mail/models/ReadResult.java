package mail.models;

public class ReadResult {

    private int messagesCountAll;
    private int messagesCountNew;
    private int messagesAlreadySeen;
    private int messagesCountSuccessful;
    private int messagesCountErrors;
    private int messagesWrongTheme;
    private int messagesWrongStructure;
    private int messagesPaymentExist;
    private int messagesWrongOrderNumber;

    public ReadResult(){
        this.messagesCountAll = 0;
        this.messagesCountNew = 0;
        this.messagesCountSuccessful = 0;
        this.messagesCountErrors = 0;
    }

    public void setMessagesCountAll(int messagesCountAll){
        this.messagesCountAll = messagesCountAll;
    }

    public int incMessagesCountNew() {
        return messagesCountNew++;
    }

    public int incMessagesAlreadySeen() {
        return messagesAlreadySeen++;
    }

    public int incMessagesCountSuccessful() {
        return messagesCountSuccessful++;
    }

    public int incMessagesCountErrors() {
        return messagesCountErrors++;
    }

    public int incMessagesWrongTheme() {
        return messagesWrongTheme++;
    }

    public int incMessagesWrongStructure() {
        return messagesWrongStructure++;
    }

    public int incMessagesPaymentExist() {
        return messagesPaymentExist++;
    }

    public int incMessagesWrongOrderNumber() {
        return messagesWrongOrderNumber++;
    }
}
