<% User user = (User) request.getSession().getAttribute("user"); %>
<%@ page import="user.UserAttempt" %>
<%@ page import="user.User" %>
<%@ page import="DAO.UserDao" %>


<html>
    <head>
        <title>Quiz History</title>
    </head>

<body>
<div class="jumbotron">
    <div class="container text-center">
        <h2>User Quiz History</h2>
        <%
        UserDao userDao = (UserDao) request.getSession().getAttribute("userDao");
        out.println("Brief Summary:");
        out.println("<p>Total number of quizzes taken: <b>" + userDao.getHistory(user).size() + "</b></p>");
        out.println("<p>Maximum score: <b>" + userDao.getMaxScore(user) + "</b></p>);
        out.println("<p>Minimum score: <b>" + userDao.getMinScore(user) + "</b></p>);
        out.println("<p>Average score: <b>" + userDao.getAverageScore(user) + "</b></p>);

        %>
    </div>
</div>

<hr>

<div class="container text-center">
    <h2>Quizzes taken by this user:</h2>
    <%
    List<UserAttempt> history = (List<UserAttempt>) request.getAttribute("history");
    if (history.size() == 0) {
        out.println("You have not taken any quizzes.");
    } else {
        for (UserAttempt ua : history) {
            String score = String.format("%.2f", ua.getScore());
            String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ua.getDateTaken());
            out.println("You took quiz " + ua.getQuizId() + " on " + date + " and recieved the score of " + score + "%.<br>");
        }
    }
    %>
</div>
<hr>

</body>
</html>