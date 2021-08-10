<% User user = (User) request.getSession().getAttribute("user"); %>


<html>

<head>
    <title><%=request.getParameter("username")%>'s Profile</title>
</head>

<body>
<div>
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
    %>
</div>
</body>

</html>