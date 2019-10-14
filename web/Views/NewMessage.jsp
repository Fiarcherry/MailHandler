<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New message</title>
</head>
<body>
    <div>
        <section>
            <form method="post" action="message?action=send">
                <dl>
                    <dt>TO: </dt>
                    <dd><input type="text" name="to" placeholder="Получатель" /></dd>
                </dl>
                <dl>
                    <dt>Message: </dt>
                    <dd><textarea name="message" rows="10" cols="100"></textarea></dd>
                </dl>
                <button type="submit">Send</button>
            </form>
        </section>
    </div>
</body>
</html>
