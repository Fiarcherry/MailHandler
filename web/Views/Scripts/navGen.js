$(document).ready(function () {
    $.ajax({
        url: "home?action=json",
        type: "POST",
        dataType: 'json',
        success: function(data){
            if (data == "NULLLOGIN") {
                $("#headerGreetings").append(" Unknown");

                $("#menu").append("<li class=\"nav-item\"><a class=\"nav-link\" href=\"auth?action=login\">Sign in</a></li>");
            }
            else {
                $("#headerGreetings").append(" " + data);

                $("#menu").append("<li class=\"nav-item dropdown\">\n" +
                    "                    <a class=\"nav-link dropdown-toggle\" href=\"#\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Read Messages</a>\n" +
                    "                    <div class=\"dropdown-menu\" aria-labelledby=\"readMessages\">\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=readMail&readType=new\">Read New Messages</a>\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=readMail&readType=all\">Read All Messages</a>\n" +
                    "                    </div>\n" +
                    "                </li>");

                $("#menu").append("<li class=\"nav-item dropdown\">\n" +
                    "                    <a class=\"nav-link dropdown-toggle tablesClass\" href=\"#\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Tables</a>\n" +
                    "                    <div class=\"dropdown-menu\" aria-labelledby=\"readMessages\">\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=show\">Payments</a>\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=showClients\">Clients</a>\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=showOrders\">Orders</a>\n" +
                    "                        <a class=\"dropdown-item\" href=\"message?action=showErrors\">Errors</a>\n" +
                    "                    </div>\n" +
                    "                </li>");

                $("#menu").append("<li class=\"nav-item\"><a class=\"nav-link\" href=\"auth?action=registration\">Sign up</a></li>");

                $("#menu").append("<li class=\"nav-item\"><a class=\"nav-link\" href=\"auth?action=logout\">Logout("+data+")</a></li>");
            }
        },
        error: function(error){
            alert(JSON.stringify(error));
        }
    });
});