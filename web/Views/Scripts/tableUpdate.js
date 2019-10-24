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
            .append($("<td class=\"col1\">").append(isProcessed?"<input type=\"checkbox\" id=\""+payment.uni+"\">":""))
            .append($("<td class=\"col2\">").text(payment.isProcessed.toString()))
            .append($("<td class=\"col3\">").text(payment.uni))
            .append($("<td class=\"col4\">").text(payment.number))
            .append($("<td class=\"col5\">").text(payment.dateOperation))
            .append($("<td class=\"col6\">").text(payment.amount))
            .append($("<td class=\"col7\">").text(payment.commission))
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