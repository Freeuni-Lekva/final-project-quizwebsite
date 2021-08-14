<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% if(request.getSession().getAttribute("currUser") == null) {
        response.sendRedirect("login.jsp");
    }%>
    welcome
</body>
</html>
