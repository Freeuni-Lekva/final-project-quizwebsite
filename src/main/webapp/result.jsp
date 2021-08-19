<%@ page import="quiz.Quiz" %>
<%@ page import="user.User" %><%--
  Created by IntelliJ IDEA.
  User: zghul
  Date: 8/20/2021
  Time: 12:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body style="background: #508bfc;">
<%
    User currUser = (User) session.getAttribute("currUser");
    if (currUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Integer maxscore = (Integer)request.getAttribute("maxscore");
    Double score = (Double)request.getAttribute("score");
%>
<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">
                    <%if (score % 1 == 0) {%>
                        <h2><%=score.intValue()%>/<%=maxscore%></h2>
                    <%} else {%>
                        <h2><%=score%>/<%=maxscore%></h2>
                    <%}%>
                    <a href="quizzes.jsp">go back to quizzes</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
