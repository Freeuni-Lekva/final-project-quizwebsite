<% User user = (User) request.getSession().getAttribute("user"); %>

<%@ page import="user.UserAttempt" %>
<%@ page import="user.User" %>


<html>

<head>
    <title><%=request.getParameter("username")%>'s Profile</title>
</head>

<body>
<div class="jumbotron">
    <div class="container text-center">
        <h2>Welcome to <%=request.getParameter("username")%>'s Profile</h2>
        <%
        int friendStatus = (int) request.getAttribute("friendStatus");
        if (friendStatus == 0) {
            out.println("<form role="form" action="FriendRequestServlet" method="post"> ");
            out.println("<div class="form-group">");
            out.println("<input type="hidden" name="curr-user" value='" + request.getParameter("username") + "'>");
            out.println("<input type="submit" name="send-friend-request" value="Send Friend Request" class="btn brn-primary" >");
            out.println("</div>");
            out.println("</form>);
        } else if (friendStatus == 1) {
            out.println("<b><i>Friend Request is already sent and Pending</i></b>");
        } else if (friendStatus == 2) {
            out.println("This user has sent you a friend request");
            out.println("<form role="form" action="FriendRequestServlet" method="post"> ");
            out.println("<div class="form-group" > ");
            out.println("<input type="hidden" name="curr-user" value='" + request.getparameter("username") + "'>");
            out.println("<input type="submit" name="accept" value="Accept Friend Request" class="btn btn-success">");
            out.println("<input type="submit" name="reject" value="Reject Friend Request" class="btn btn-danger" >");
            out.println("</div>");
            out.println("</form>");
        } else if (friendStatus == 3) {
            out.println("This user is your friend.");
            out.println("<form role="form" action="FriendRequestServlet" method="post" >");
            out.println("<div class="form-group" >");
            out.println("<input type="hidden" name="curr-user" value='" + request.getParameter("username") + "'>");
            out.println("<input type='submit' name='remove-friend' value='Remove friend' class='btn btn-danger'>");
            out.println("</div>");
            out.println("</form>");
        }


        if (!(user.getUsername().equals(request.getParameter("username")))) {
            out.println("<form class="send-message" action="SendMessageServlet" method="post" >");
            out.println("<div class="form-group">");
            out.println("<p>Send a message to this user.</p>");
            out.println("<label>Subject</label><br>");
            out.println("<input type="text" name="subject" value=""><br><br>");
            out.println("<label>Text Message</label>");
            out.println("<textarea class="form-control" rows="8" name="text-message"></textarea><br>");
            out.println("<input type="submit" name="send-message" value="Send Message" class="btn btn-success">");
            out.println("<input type="hidden" name="curr-user" value='" + request.getParameter("username) + "'>");
            out.println("</div>");
            out.println("</form>");
        }
        %>
    </div>
</div>

<hr>

<div class="container">
    <h2>User History</h2>
    <div class="row">
        <%
        List<UserAttempt> history = (List<UserAttempt>) request.getAttribute("history");
        if (history.size() == 0) out.println("This user has not taken any quizzes.")
        else {
            for (UserAttempt ua : history) {
                String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ua.getDate());
                out.println(request.getParameter("username") + "took quiz " + ua.getQuizId() + " on " + date + " and
                    has a score of " + ua.getScore() + ".");
                out.println("<br>");
            }
        }

        %>
    </div>
</div>

</body>

</html>