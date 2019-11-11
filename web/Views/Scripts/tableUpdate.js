function callAjax(){
    var checked = [];
    $('input:checkbox:checked').each(function() {
        checked.push($(this).attr("id"));
    });
    if (checked.length <= 0) {
        drawError("Check at least one row.");
        return;
    }
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
            alert(error);
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
        var isProcessed = payment.IsProcessed == "0";
        $("<tr class='drawable'>").appendTo($tBody)
            .append($("<td>").append(isProcessed?"<input type=\"checkbox\" id=\""+payment.paymentID+"\">":""))
            .append($("<td>").text(payment.IsProcessed == "1"? "Sent" : "Not Sent"))
            .append($("<td>").text(payment.Email))
            .append($("<td>").text(payment.FirstName))
            .append($("<td>").text(payment.SecondName))
            .append($("<td>").text(payment.DatePayment))
            .append($("<td>").text(payment.AmountPayment))
    });
    $("#table td").each(function() {
        if ($(this).text() == 'Not Sent') {
            $(this).addClass("table-danger");
        }
        if ($(this).text() == 'Sent') {
            $(this).addClass("table-success");
        }
    });
}

function drawError(data) {
    alert(data);
}