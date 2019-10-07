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

function drawError(data) {
    alert(data);
}