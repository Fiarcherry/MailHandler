package mail.models;

/**
 * Модель результатов чтения сообщений
 */
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

    /**
     * Конструктор результата чтения сообщений
     */
    public ReadResult(){
        this.messagesCountAll = 0;
        this.messagesCountNew = 0;
        this.messagesCountSuccessful = 0;
        this.messagesCountErrors = 0;
    }

    /**
     * Записать количество всех сообщений на почте
     * @param messagesCountAll Количество всех сообщений на почте
     */
    public void setMessagesCountAll(int messagesCountAll){
        this.messagesCountAll = messagesCountAll;
    }

    /**
     * Инкрементировать количество новых сообщений
     * @return Количество новых сообщений
     */
    public int incMessagesCountNew() {
        return messagesCountNew++;
    }

    /**
     * Инкрементировать количество уже прочитанных сообщений
     * @return Количество уже прочитанных сообщений
     */
    public int incMessagesAlreadySeen() {
        return messagesAlreadySeen++;
    }

    /**
     * Инкрементировать количество добавленных в базу данных сообщений
     * @return Количество добавленных в базу данных сообщений
     */
    public int incMessagesCountSuccessful() {
        return messagesCountSuccessful++;
    }

    /**
     * Инкрементировать количество сообщений с ошибками
     * @return Количество сообщений с ошибками
     */
    public int incMessagesCountErrors() {
        return messagesCountErrors++;
    }

    /**
     * Инкрементировать количество сообщений с неверной темой сообщения
     * @return Количество сообщений с неверной темой сообщения
     */
    public int incMessagesWrongTheme() {
        return messagesWrongTheme++;
    }

    /**
     * Инкрементировать количество сообщений с неверной структурой XML-файла
     * @return Количество сообщений с неверной структурой XML-файла
     */
    public int incMessagesWrongStructure() {
        return messagesWrongStructure++;
    }

    /**
     * Инкрементировать количество уже существующих сообщений
     * @return Количество уже существующих сообщений
     */
    public int incMessagesPaymentExist() {
        return messagesPaymentExist++;
    }

    /**
     * Инкрементировать количество сообщений с неверным номером зказа
     * @return Количество сообщений с неверным номером зказа
     */
    public int incMessagesWrongOrderNumber() {
        return messagesWrongOrderNumber++;
    }
}
