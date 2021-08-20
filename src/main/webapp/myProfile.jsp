<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="quiz.Quiz" %>
<%@ page import="user.UserAttempt" %>
<%@ page import="DAO.QuizDao" %>
<%@ page import="mailbox.FriendRequest" %>
<%@ page import="DAO.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<%
    boolean isCurrUserAdmin = false;
    long currUserId = -1;
    String currUsername = "";
    String currName = "";
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
        return;
    } else {
        currName = ((User)session.getAttribute("currUser")).getFirstName() + " " + ((User)session.getAttribute("currUser")).getLastName();
        isCurrUserAdmin = ((User)session.getAttribute("currUser")).isAdmin();
        currUsername = ((User)session.getAttribute("currUser")).getUsername();
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
            <a class="nav-item nav-link active" href="<%=request.getContextPath()%>/UserServlet?username=<%=currUsername%>">My Profile <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="<%=request.getContextPath()%>/FriendRequestsServlet?userId=<%=currUserId%>">Friend Requests</a>
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
                                <h4><%=currUsername%></h4>
                                <p class="text-secondary mb-1"><%=currName%></p>
                                <a href="<%=request.getContextPath()%>/MessengerServlet?from_user_id=<%=currUserId%>" class="btn btn-outline-primary">Message</a>
                            </div>
                        </div>
                        <hr class="my-4">
                        <div class="d-flex flex-column align-items-center text-center">
                            <div class="mt-3">
                                <%if (isCurrUserAdmin) {%>
                                <p class="text-secondary mb-1">Admin</p>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card" style="min-height: 200px;">
                    <div class="card-body">
                        <h5 class="d-flex align-items-center mb-3">Attempted Quizzes</h5>
                        <div class="row mb-3">
                            <div class="col">
                                <label class="mb-0">Quiz Name</label>
                            </div>
                            <div class="col text-secondary">
                                Score
                            </div>
                            <div class="col text-secondary">
                                Attempt Time
                            </div>
                        </div>
                        <%
                            List<UserAttempt> attempts = (List<UserAttempt>) request.getAttribute("attempts");
                            if (attempts != null) {
                                for (UserAttempt q : attempts) {
                                    QuizDao dao = (QuizDao)request.getServletContext().getAttribute("QuizDao");
                                    Quiz quiz = dao.getQuiz(q.getQuizId());
                                    out.println("<div class='row mb-3'>");
                                    out.println("<div class='col'>");
                                    out.println("<a class='mb-0'" + "href='QuizServlet?quizId=" + quiz.getId() + "'>" + quiz.getName() + "</a>");
                                    out.println("</div>");
                                    out.println("<div class='col text-secondary'>" + q.getScore() + "</div>");
                                    out.println("<div class='col text-secondary'>" + q.getTimeStamp() + "</div> </div>");
                                }
                            }
                        %>

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card" style="min-height:200px;">
                            <div class="card-body">
                                <h5 class="d-flex align-items-center mb-3">Created Quizzes</h5>
                                <%
                                    List<Quiz> quizzes = (List<Quiz>) request.getAttribute("createdQuizzes");
                                    if (quizzes != null) {
                                        for (Quiz q : quizzes) {
                                            out.println("<div class='col'> <a class='mb-0' href='QuizServlet?quizId=" + q.getId() + "'>" + q.getName() + "</a> </div>");
                                        }
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card" style="min-height:200px;">
                            <div class="card-body">
                                <h5 class="d-flex align-items-center mb-3">Friends</h5>

                                <%
                                    List<User> friendlist = (List<User>) request.getAttribute("friendList");
                                    if (friendlist != null) {
                                        for (User u : friendlist) {
                                            out.println("<div class='col'> <a class='mb-0' href='UserServlet?username=" + u.getUsername() + "'>" + u.getUsername() + "</a> </div>");
                                        }
                                    }
                                %>

                            </div>
                        </div>
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

</script>
</body>
</html>
</body>
</html>
