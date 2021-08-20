<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="quiz.Quiz" %>
<%@ page import="user.UserAttempt" %>
<%@ page import="DAO.QuizDao" %>
<%@ page import="mailbox.FriendRequest" %>
<%@ page import="DAO.UserDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.FriendRequestDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<%
    boolean isCurrUserAdmin;
    long currUserId;
    String currUsername;
    User currUser;
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
        return;
    } else {
        currUser = (User)session.getAttribute("currUser");
        isCurrUserAdmin = currUser.isAdmin();
        currUsername = currUser.getUsername();
        currUserId = currUser.getId();
    }
    User user = (User) request.getAttribute("user");
    String username = "";
    String name = "";
    long userId = -1;
    boolean isAdmin = false;
    if (user != null) {
        isAdmin = user.isAdmin();
        username = user.getUsername();
        name = user.getFirstName() + " " + user.getLastName();
        userId = user.getId();
    } else {
        response.sendRedirect("pageNotFound.jsp");
    }
    if (username.equals(currUsername)) {
        request.getRequestDispatcher("myProfile.jsp").forward(request,response);
        return;
    }
    List<User> friendList = (List<User>) request.getAttribute("friendList");

    FriendRequestDao friendRequestDao = (FriendRequestDao) request.getServletContext().getAttribute("FriendRequestDao");
    UserDao userDao = (UserDao) request.getServletContext().getAttribute("UserDao");

    List<FriendRequest> requests = friendRequestDao.getFriendRequests(currUserId);
    List<User> friendReqs= new ArrayList<User>();
    for (FriendRequest req : requests) {
        friendReqs.add(userDao.getUser(req.getFromId()));
    }

    List<FriendRequest> requests1 = friendRequestDao.getFriendRequests(userId);
    List<User> friendReqs1 = new ArrayList<User>();
    for (FriendRequest req : requests1) {
        friendReqs1.add(userDao.getUser(req.getFromId()));
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
                                <h4><%=username%></h4>
                                <p class="text-secondary mb-1"><%=name%></p>
                                    <form action="UserServlet" method="post">
                                        <input type="hidden" name="userId" value="<%=userId%>">
                                        <input type="hidden" name="username" value="<%=username%>">
                                               <%System.out.println(friendList);%>
                                        <%if (friendReqs1.contains(currUser)) {%>
                                        <label>Friend Request Sent</label>
                                        <%} else if (friendReqs.contains(user)) {%>
                                        <input type="submit" name="respondReq" class="btn btn-primary" value="Respond Request">
                                        <%} else if (!friendList.contains(currUser)) {%>
                                        <input type="submit" name="sendReq" class="btn btn-primary" value="Add Friend">
                                        <%}%>


                                    </form>
                                <a href="<%=request.getContextPath()%>/MessengerServlet?from_user_id=<%=userId%>" class="btn btn-outline-primary">Message</a>
                            </div>
                        </div>
                        <hr class="my-4">
                        <div class="d-flex flex-column align-items-center text-center">
                            <div class="mt-3">
                                <%if (isAdmin) {%>
                                <p class="text-secondary mb-1">Admin</p>
                                <%}%>
                            </div>
                        </div>

                        <%if (isCurrUserAdmin && !isAdmin) {%>
                            <div class="d-flex flex-column align-items-center text-center">
                                <div class="mt-3">
                                    <form action="AdminServlet" method="post">
                                        <input type="hidden" name="userId" value="<%=userId%>">
                                        <input name="makeAdmin" value="Make Admin" type="submit" class="btn btn-success">
                                        <input name="deleteUser" class="btn btn-danger" type="submit" value="Delete User">
                                    </form>

                                </div>
                            </div>
                        <%} else if (isCurrUserAdmin) {%>
                        <div class="d-flex flex-column align-items-center text-center">
                            <div class="mt-3">
                                <form action="AdminServlet" method="post">
                                    <input type="hidden" name="userId" value="<%=userId%>">
                                    <input name="deleteUser" class="btn btn-danger" type="submit" value="Delete User">
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
                                        if (friendList != null) {
                                            for (User u : friendList) {
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
