<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<script src="/jQuery.js"></script>--%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<html>
<head>
    <title>Payments</title>
</head>
<body>
<table>
    <thead>
        <tr>
            <th>Uri</th>
            <th>Number</th>
            <th>DateOperation</th>
            <th>Account</th>
            <th>Amount</th>
            <th>Commission</th>
        </tr>
    </thead>
    <tbody id="tableBody">
        <script>
            $(window).on("load", function () {
                $.get("MessageServlet", function (resp) {
                    alert("alert");
                    alert(resp);
                    var $tBody = $("<tbody>").appendTo($("#tableBody"));
                    $.each(resp, function (index, payment) {
                        $("<tr>").appendTo($tBody)
                            .append($("<td>").text(payment.uri))
                            .append($("<td>").text(payment.number))
                            .append($("<td>").text(payment.dateOperation))
                            .append($("<td>").text(payment.account))
                            .append($("<td>").text(payment.amount))
                            .append($("<td>").text(payment.commission))
                    })
                });
            });
        </script>
    </tbody>

</table>

</body>
</html>
