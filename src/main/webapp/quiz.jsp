<%@ page import="quiz.Quiz" %>
<%@ page import="user.User" %>
<%@ page import="quiz.QuizAttempt" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.UserDao" %>
<%--
  Created by IntelliJ IDEA.
  User: zghul
  Date: 8/16/2021
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%
    User currUser = (User) session.getAttribute("currUser");
    if (currUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean isCurrUserAdmin = currUser.isAdmin();
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    String quizName = "";
    if (quiz == null) {
        response.sendRedirect("quizzes.jsp");
        return;
    }
    quizName = quiz.getName();
    User user = quiz.getAuthor();
    int currUserId = (int)currUser.getId();
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-top:0; margin-bottom : 1.5%">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/quizzes.jsp">Quiz Website</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="<%=request.getContextPath()%>/UserServlet?username=<%=currUser.getUsername()%>">My Profile <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="<%=request.getContextPath()%>/FriendRequestsServlet?userId=<%=currUser.getId()%>">Friend Requests</a>
            <a class="nav-item nav-link" href="<%=request.getContextPath()%>/addQuizServlet?userId=<%=currUserId%>">Create Quiz</a>
            <a class="nav-item nav-link mr-auto" href="<%=request.getContextPath()%>/LogoutServlet">Sign out</a>

        </div>
    </div>
</nav>

<div class="container">
    <div class="main-body">
        <div class="row">
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="https://cdn.iconscout.com/icon/free/png-256/user-1648810-1401302.png" class="rounded-circle p-1 bg-primary" width="110">
                            <div class="mt-3">
                                <h4><%=quizName%></h4>
                                <a href="<%=request.getContextPath()%>/UserServlet?username=<%=user.getUsername()%>" class="text-secondary mb-1">By: <%=user.getUsername()%></a>
                                <br>max score: <%=quiz.getQuestions().size()%>
                            </div>
                            <form action="QuizTakeServlet" method = 'get'>
                                <input type="hidden" name="quizId" value=<%=quiz.getId()%>>
                                <input name="startAttempt" value="Start Attempt" type="submit" class="btn btn-success">
                            </form>

                        </div>
                        <hr class="my-4">


                        <%if (isCurrUserAdmin) {%>
                        <div class="d-flex flex-column align-items-center text-center">
                            <div class="mt-3">
                                <form action="AdminServlet" method="post">
                                    <input type="hidden" name="quizId" value="<%=quiz.getId()%>">
                                    <input name="deleteQuiz" value="Delete Quiz" type="submit" class="btn btn-danger">
                                </form>
                            </div>
                        </div>
                        <%}%>

                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card" style="min-height: 200px;">
                    <div class="card-body">
                        <h5 class="d-flex align-items-center mb-3">Quiz History</h5>
                        <div class="row mb-3">
                            <div class="col">
                                <label class="mb-0">User</label>
                            </div>
                            <div class="col text-secondary">
                                Score
                            </div>
                            <div class="col text-secondary">
                                Attempt Time
                            </div>
                        </div>
                        <%
                            List<QuizAttempt> history = quiz.getHistory();
                            if (history != null) {
                                for (QuizAttempt q : history) {
                                    UserDao dao = (UserDao)request.getServletContext().getAttribute("UserDao");
                                    User trier = dao.getUser(q.getUserId());
                                    out.println("<div class='row mb-3'>");
                                    out.println("<div class='col'>");
                                    out.println("<a class='mb-0' href='UserServlet?username=" + trier.getUsername() + "'>" + trier.getUsername() + "</a>");
                                    out.println("</div>");
                                    out.println("<div class='col text-secondary'>" + q.getScore() + "</div>");
                                    out.println("<div class='col text-secondary'>" + q.getTimestamp() + "</div> </div>");
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style type="text/css">
    body{
        background: #508bfc;
    }
    .card {
        position: relative;
        display: flex;
        flex-direction: column;
        min-width: 0;
        word-wrap: break-word;
        background-color: #fff;
        background-clip: border-box;
        border: 0 solid transparent;
        border-radius: .25rem;
        margin-bottom: 1.5rem;
        box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
    }
    .me-2 {
        margin-right: .5rem!important;
    }
</style>
</body>
</html>


