<%@ page import="user.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: zghul
  Date: 8/16/2021
  Time: 1:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Friend Requests</title>
</head>
<body>
<%
    long currUserId = -1;
    String currUsername = "";
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
    } else {
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
<section class="vh-100" style="background-color: #508bfc;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="card shadow-2-strong" style="border-radius: 1rem; width: 80%; min-height: 80%;" >
                <div class="card-body p-5 text-center">

                    <%
                        List<User> reqs = (List<User>) request.getAttribute("reqs");
                        if (reqs != null) {
                            for (User u : reqs) {
                                out.println("<li><a href='/UserServlet?username=" + u.getUsername() + "'>" + u.getUsername() + "</a>");
                                out.println("<form action='FriendRequestsServlet' method='post'>");
                                out.println("<input type='hidden' name='userId' value='" + u.getId() + "'>");
                                out.println("<input  class='btn btn-primary'  type='submit' name='accept' value='accept'>");
                                out.println("<input  class='btn btn-danger'  type='submit' name='decline' value='delete'>");
                                out.println("</form></li>");
                            }
                        } else {
                            return;
                        }
                        if (reqs.isEmpty()) {
                            out.println("<h3>No friend requests</h3>");
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>