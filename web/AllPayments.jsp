<%--<script src="/jQuery.js"></script>--%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<html>
<head>
    <title>Payments</title>
</head>
<body>
<table id="table" border="1">
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
        <script type="text/javascript">
            $(document).ready(function () {
                $.getJSON("message?action=json", function (data) {
                    var $tBody = $("<tbody>").appendTo($("#table"));
                    $.each(data, function (index, payment) {
                        $("<tr>").appendTo($tBody)
                            .append($("<td>").text(payment.uni))
                            .append($("<td>").text(payment.number))
                            .append($("<td>").text(payment.dateOperation))
                            .append($("<td>").text(payment.account))
                            .append($("<td>").text(payment.amount))
                            .append($("<td>").text(payment.commission))
                    })
                });
            });
        </script>

</table>

</body>
</html>
