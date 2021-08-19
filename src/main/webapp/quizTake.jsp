<%@ page import="user.User" %>
<%@ page import="DAO.QuizDao" %>
<%@ page import="database.DatabaseConnection" %>
<%@ page import="quiz.Quiz" %>
<%@ page import="quiz.RandomOrderQuiz" %>
<%@ page import="quiz.StandardQuiz" %>
<%@ page import="java.util.List" %>
<%@ page import="question.Question"%>
<%@ page import="java.util.HashSet"%>

<%@ page import="question.PictureUnorderedResponseQuestion"%>
<%@ page import="question.MultipleChoiceUnorderedResponseQuestion"%>
<%@ page import="question.MultipleAnswerUnorderedResponseQuestion" %>
<%@ page import="question.StandardUnorderedResponseQuestion"%>

<%@ page import="DAO.MultipleAnswerUnorderedResponseQuestionDao" %>
<%@ page import="DAO.MultipleChoiceUnorderedResponseQuestionDao" %>
<%@ page import="DAO.PictureUnorderedResponseQuestionDao" %>
<%@ page import="DAO.StandardUnorderedResponseQuestionDao"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Recent Quizzes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body style="background: #508bfc;">
<%
        User currUser = (User) session.getAttribute("currUser");
        if (currUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Question> questions = (List<Question>)request.getAttribute("questionList");
        if(questions == null){
            response.sendRedirect("quizzes.jsp");
            return;
        }
%>

<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="card shadow-2-strong " style="border-radius: 1rem; width : 90% ;padding:5%">
            <form action='QuizTakeServlet' method='post'>
                <input type="hidden" name="quizId" value="<%=request.getParameter("quizId")%>">
                <%for (int i = 0; i < questions.size(); i++) {%>
                    <label> <%=i + 1%>) <%=questions.get(i).getQuestionText() %></label>
                    <% if (questions.get(i).getClass() == StandardUnorderedResponseQuestion.class) {%>
                        <input required type="text" name="<%=i%>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                    <%} else if (questions.get(i).getClass() == MultipleAnswerUnorderedResponseQuestion.class) {%>
                        <%MultipleAnswerUnorderedResponseQuestion question = (MultipleAnswerUnorderedResponseQuestion) questions.get(i);
                        for (int j = 0; j < question.getNumOfRequestedAnswers(); j++) { %>
                            <input required type="text" name="<%=i%>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                        <%}%>
                    <%} else if (questions.get(i).getClass() == MultipleChoiceUnorderedResponseQuestion.class) {%>
                        <div style='padding: 10px;'>
                        <%MultipleChoiceUnorderedResponseQuestion question = (MultipleChoiceUnorderedResponseQuestion) questions.get(i);
                        int j = 0;
                        for(String ans : question.getChoices()) {%>
                            <input type='radio' name='<%=i%>' value="<%=ans%>" id='<%=j%>' style='transform: scale(1.6); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />
                            <label for="<%=j%>"> <%=ans%></label> <br>
                        <%j++;}%>
                        </div>
                    <%} else if (questions.get(i).getClass() == PictureUnorderedResponseQuestion.class) {%>
                        <input required type="text" name="<%=i%>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                    <%}%>
                    <hr>
                <%}%>
                <input class="btn btn-success" type="submit" value="Submit">
            </form>
        </div>
    </div>
</div>

</body>
</html>