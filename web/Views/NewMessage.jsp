<%--
  Created by IntelliJ IDEA.
  User: sanya
  Date: 23.09.2019
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
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
            <form method="post" action="message?action=read">
                <button type="submit">Read</button>
            </form>
        </section>
    </div>
</body>
</html>
