<%@ page import="user.User" %>
<%@ page import="DAO.QuizDao" %>
<%@ page import="database.DatabaseConnection" %>
<%@ page import="quiz.Quiz" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: zghul
  Date: 8/15/2021
  Time: 9:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recent Quizzes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body style="background: #508bfc;">
<%
    QuizDao quizDao = new QuizDao(DatabaseConnection.getConnection());
    List<Quiz> quizzes = quizDao.getQuizzes();
    String username = "";
    long currUserId = -1;
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
    } else {
        username = ((User)session.getAttribute("currUser")).getUsername();
        currUserId = ((User)session.getAttribute("currUser")).getId();
    }
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-top:0; margin-bottom : 1.5%">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/quizzes.jsp">Quiz Website</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="<%=request.getContextPath()%>/UserServlet?username=<%=username%>">My Profile <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="<%=request.getContextPath()%>/FriendRequestsServlet?userId=<%=currUserId%>">Friend Requests</a>
            <a class="nav-item nav-link" href="<%=request.getContextPath()%>/addQuizServlet?userId=<%=currUserId%>">Create Quiz</a>
            <a class="nav-item nav-link mr-auto" href="<%=request.getContextPath()%>/LogoutServlet">Sign out</a>
        </div>
    </div>
</nav>



<%int counter = 0;
    for (Quiz quiz : quizzes) {
        if (counter % 3 == 0) {
            out.println("<div class='container'>");
            out.println("<div class='row'>");
        }
        out.println("<div class='col-lg-4 mb-4'>");
        out.println("<div class='card'>");
        out.println("<img src='images/quiz.jpg' class='card-img-top'>");
        out.println("<div class='card-body'>");
        out.println("<h5 class='card-title'>" + quiz.getName() + "</h5>");
        out.println("<a href='" + request.getContextPath() + "/UserServlet?username=" + quiz.getAuthor().getUsername() + "'" + "' class='card-text'>" + quiz.getAuthor().getUsername() + "</a>");
        out.println("<a href='" + request.getContextPath() + "/QuizServlet" +
                "?quizId=" + quiz.getId() + "' class='btn btn-outline-success btn-sm'>Quiz Page</a> </div> </div> </div>");
        if (counter % 3 == 2) {
            out.println("</div> </div>");
        }
        counter++;

        if(counter==quizzes.size()){
                if (counter % 3 == 0) {
                  out.println("<div class='container'>");
                  out.println("<div class='row'>");
                  }
                  out.println("<div class='col-lg-4 mb-4'>");
                  out.println("<div class='card'>");
                  out.println("<img src='images/quiz.jpg' class='card-img-top'>");
                  out.println("<div class='card-body'>");
                  out.println("<h5 class='card-title'>"  + "</h5>");
                  out.println("<a href='" + request.getContextPath() + "/addQuizServlet" +
                          "?quizId=" + "' class='btn btn-outline-success btn-sm'>Create New</a> </div> </div> </div>");
            if (counter % 3 == 2) {
                 out.println("</div> </div>");
            }
        }

    }

%>

</body>
</html>
