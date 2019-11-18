package mail.models;

/**
 * Модель сообщения для отправки
 */
public class EMessage {

    private String to;
    private String messageText;
    private String subject;

    /**
     * Конструктор сообщения для отправки
     * @param to Кому отправить
     * @param subject Тема сообщения
     * @param messageText Содержание сообщения
     */
    public EMessage(String to, String subject, String messageText) {
        this.to = to;
        this.messageText = messageText;
        this.subject = subject;
    }

    /**
     * Читать получателя
     * @return Получатель
     */
    public String getTo() {
        return to;
    }

    /**
     * Читать содержание сообщения
     * @return Содержание сообщения
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Читать тему сообщения
     * @return Тема сообщения
     */
    public String getSubject() {
        return subject;
    }
}
