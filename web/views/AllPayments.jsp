<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payments</title>
</head>
<body>
<table>
    <tr>
        <th>Uri</th>
        <th>Number</th>
        <th>DateOperation</th>
        <th>Account</th>
        <th>Amount</th>
        <th>Commission</th>
    </tr>
    <c:forEach var="payment" items="${payments}">
        <tr>
            <td><c:out value="${payment.getUri()}"/></td>
            <td><c:out value="${payment.getNumber()}"/></td>
            <td><c:out value="${payment.getDateOperation()}"/></td>
            <td><c:out value="${payment.getAccount()}"/></td>
            <td><c:out value="${payment.getAmount()}"/></td>
            <td><c:out value="${payment.getUgetCommissionri()}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
