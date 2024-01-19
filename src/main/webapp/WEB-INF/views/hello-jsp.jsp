<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <p>data(EL문법) : ${myData}</p>
    <p>data(java코드) :
    <%
    String getData = (String)request.getAttribute("myData");
    out.print(getData);
    %></p>
</body>
</html>