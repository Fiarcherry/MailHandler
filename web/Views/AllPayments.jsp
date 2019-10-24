<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Views/Scripts/jquery.simple-checkbox-table.js"></script>
<script src="${pageContext.request.contextPath}/Views/Scripts/tableUpdate.js"></script>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Payments</title>
    <link rel="stylesheet" type="text/css" href="css/PaymentsTable.css">
</head>
<body>
<a href="auth?action=logout">Logout</a><br>
<table id="table" class="tg" border="1">
    <thead>
    <tr>
        <th class="col1"><input type="checkbox" id="checkBoxMaster"></th>
        <th class="col2">State</th>
        <th class="col3">Uni</th>
        <th class="col4">Number</th>
        <th class="col5">Operation Date</th>
        <th class="col7">Amount</th>
        <th class="col8">Commission</th>
    </tr>
    </thead>
    <tbody id='tableBody'>
    <script type="text/javascript">
        $(document).ready(function () {
            $.getJSON("message?action=json", function (data) {
                drawTBody(data);
            });
        });
    </script>
    <script>
        $('#checkBoxMaster').click(function () {
            if ($(this).is(':checked')) {
                $('#table input:checkbox').prop('checked', true);
            } else {
                $('#table input:checkbox').prop('checked', false);
            }
        });
    </script>
    </tbody>
</table>
<button id="sendCheckedButton" onclick="callAjax()">Send</button>
</body>
</html>
