<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://raw.githubusercontent.com/toshiyukihina/jquery-simple-checkbox-table/master/dist/jquery.simple-checkbox-table.js"></script>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Payments</title>
</head>
<body>
<table id="table" border="1">
    <thead>
        <tr>
            <th><input type="checkbox" id="checkBoxMaster"></th>
            <th>State</th>
            <th>Uni</th>
            <th>Number</th>
            <th>Operation Date</th>
            <th>Account num</th>
            <th>Amount</th>
            <th>Commission</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody id='tableBody'>
        <script type="text/javascript">
            $(document).ready(function () {
                $.getJSON("message?action=json", function (data){
                    drawTBody(data);
                });
            });
        </script>
        <script>
            $('#checkBoxMaster').click(function(){
                if ($(this).is(':checked')){
                    $('#table input:checkbox').prop('checked', true);
                } else {
                    $('#table input:checkbox').prop('checked', false);
                }
            });
        </script>
    </tbody>
</table>
<button id="sendCheckedButton" onclick="callAjax()">Send</button>
<script>
    function callAjax(){
        var checked = [];
        $('input:checkbox:checked').each(function() {
            checked.push($(this).attr("id"));
        });
        if (checked.includes("checkBoxMaster"))
            checked.splice(checked.indexOf("checkBoxMaster"), 1);

        $.ajax({
            url: "message?action=json",
            type: "POST",
            dataType: 'json',
            success: function(data){
                removeRows();
                drawTBody(data);
            },
            error: function(error){
                alert(JSON.stringify(error));
            },
            data: {json: JSON.stringify(checked)}
        });
    }

    function removeRows() {
        $(".drawable").remove();
    }

    function drawTBody(data) {
        var $tBody = $("#tableBody");
        $.each(data, function (index, payment) {
            var isProcessed = payment.isProcessed.toString() == 'false';
            $("<tr class='drawable'>").appendTo($tBody)
                .append($("<td>").append(isProcessed?"<input type=\"checkbox\" id=\""+payment.uni+"\">":""))
                .append($("<td>").text(payment.isProcessed.toString()))
                .append($("<td>").text(payment.uni))
                .append($("<td>").text(payment.number))
                .append($("<td>").text(payment.dateOperation))
                .append($("<td>").text(payment.account))
                .append($("<td>").text(payment.amount))
                .append($("<td>").text(payment.commission))
                .append($("<td>").text(payment.email))
        });
        $("#table td").each(function() {
            if ($(this).text() == 'false') {
                $(this).css('background-color', 'red');
            }
            if ($(this).text() == 'true') {
                $(this).css('background-color', 'green');
            }
        });
    }
</script>
</body>
</html>
