package mail.models;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Авторизация почты
 */
public class EmailAuthenticator extends Authenticator {

    private String login;
    private String password;

    /**
     * Конструктор почты
     * @param login Логин
     * @param password Пароль
     */
    public EmailAuthenticator (final String login, final String password)
    {
        this.login    = login;
        this.password = password;
    }

    /**
     * Авторизация почты
     * @return Ответ авторизации
     */
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(login, password);
    }
}
