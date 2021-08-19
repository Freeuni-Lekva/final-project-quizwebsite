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
        long qId =  (long)Long.parseLong( request.getParameter("quizId"));
        QuizDao quizDao = new QuizDao(DatabaseConnection.getConnection());
        Quiz quiz = quizDao.getQuiz(qId);
        if (quiz == null) {
            response.sendRedirect("quizzes.jsp");
            return;
        }

        MultipleAnswerUnorderedResponseQuestionDao qDao1= new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        MultipleChoiceUnorderedResponseQuestionDao qDao2= new MultipleChoiceUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        PictureUnorderedResponseQuestionDao qDao3 = new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        StandardUnorderedResponseQuestionDao qDao4 = new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());

        List<MultipleAnswerUnorderedResponseQuestion> q1 = qDao1.getQuestionsMultipleAnsUnordered(qId);
        List<MultipleChoiceUnorderedResponseQuestion> q2 = qDao2.getQuestionsWithChoices(qId);
        List<PictureUnorderedResponseQuestion> q3 = qDao3.getQuestionsPictureUnordered(qId);
        List<StandardUnorderedResponseQuestion> q4 = qDao4.getQuestionsStandardUnordered(qId);


        List<Question> questions = (List<Question>)session.getAttribute("questions");
        if(questions==null){
            questions =  (List<Question>)request.getAttribute("questions");
            session.setAttribute("questions", questions);
        }

         Double score = (Double)request.getAttribute("score");
         if(score==null){
            score =0.0;
         }
         int ind=0;
         if(request.getParameter("index")!=null){
            ind = (int)Long.parseLong(request.getParameter("index"));
         }

%>

    <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">
                            <% if(questions!=null){
                                   if(ind<questions.size()){
                                        Question q = questions.get(ind); %>

                                            <%for(int a=0; a<q1.size(); a++){
                                                if (q1.get(a).getQuestionText().equals(q.getQuestionText())){
                                                    MultipleAnswerUnorderedResponseQuestion question1 = q1.get(a);
                                                    long n = question1.getNumOfRequestedAnswers();%>
                                                    <form action='QuizTakeServlet' method='get'>
                                                        <label><%=question1.getQuestionText()%></label>
                                                        <% for(int i=0; i<(int)n; i++){%>
                                                            <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <%}%>
                                                        <input type="hidden" name="quizId" value=<%=qId%>>
                                                        <input type="hidden" name="type" value=1>
                                                        <input type="hidden" name="form" value=1>
                                                        <input type='hidden' name='index' value=<%=ind+1%>>
                                                        <input type='hidden' name='score' value=<%=score%>>
                                                        <input type='hidden' name='multipleChoice' value=0>
                                                        <input class="btn btn-success" type="submit" value="Next">
                                                    </form>
                                               <%}
                                            }%>

                                            <%for(int k=0; k<q2.size(); k++){
                                                if (q2.get(k).getQuestionText().equals(q.getQuestionText())){
                                                    HashSet<String> choices= ((MultipleChoiceUnorderedResponseQuestion)q).getChoices();%>
                                                    <form action='QuizTakeServlet' method='get'>
                                                        <label><%=q.getQuestionText()%></label>
                                                            <%for (String s : choices){%>
                                                                <div id='block-11' style='padding: 10px;'>
                                                                 <label for="answer" >
                                                                    <input type='radio' name='answer' value=<%=s%> id='answer' style='transform: scale(1.6); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />
                                                                 <%=s%></label><br>
                                                                 </div>
                                                            <%}%>
                                                        <input type="hidden" name="quizId" value=<%=qId%>>
                                                        <input type="hidden" name="type" value=2>
                                                        <input type="hidden" name="form" value=1>
                                                        <input type='hidden' name='index' value=<%=ind+1%>>
                                                        <input type='hidden' name='score' value=<%=score%>>
                                                        <input type='hidden' name='multipleChoice' value=0>
                                                        <input class="btn btn-success" type="submit" value="Next">
                                                    </form>
                                               <%}
                                            }%>


                                            <%for(int k=0; k<q3.size(); k++){
                                                if (q3.get(k).getQuestionText().equals(q.getQuestionText())){%>
                                                    <form action='QuizTakeServlet' method='get'>
                                                        <img src=<%=((PictureUnorderedResponseQuestion)q).getPicUrl()%> class='card-img-top'>
                                                        <label><%=q.getQuestionText()%></label>
                                                        <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <input type="hidden" name="quizId" value=<%=qId%>>
                                                        <input type="hidden" name="type" value=3>
                                                        <input type="hidden" name="form" value=1>
                                                        <input type='hidden' name='index' value=<%=ind+1%>>
                                                        <input type='hidden' name='score' value=<%=score%>>
                                                        <input type='hidden' name='multipleChoice' value=0>
                                                        <input class="btn btn-success" type="submit" value="Next">
                                                    </form>
                                               <%}
                                            }%>

                                            <%for(int k=0; k<q4.size(); k++){
                                                if (q4.get(k).getQuestionText().equals(q.getQuestionText())){%>
                                                    <form action='QuizTakeServlet' method='get'>
                                                        <label><%=q.getQuestionText()%></label>
                                                        <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <input type="hidden" name="quizId" value=<%=qId%>>
                                                        <input type="hidden" name="form" value=1>
                                                        <input type="hidden" name="type" value=4>
                                                        <input type='hidden' name='index' value=<%=ind+1%>>
                                                        <input type='hidden' name='score' value=<%=score%>>
                                                        <input type='hidden' name='multipleChoice' value=0>
                                                        <input class="btn btn-success" type="submit" value="Next">
                                                    </form>
                                               <%}
                                            }%>
                                   <%}else{%>
                                        <label><%=score%></label>
                                        <form action='QuizTakeServlet' method='post'>
                                            <input class="btn btn-success" type="submit" value="User page">
                                            <input type='hidden' name='score' value=<%=score%>>
                                            <input type='hidden' name='userId' value=<%=currUser.getId()%>>
                                            <input type="hidden" name="quizId" value=<%=qId%>>
                                        </form>
                                   <%}
                            }%>

                        </div>
                    </div>
                </div>
            </div>
    </div>

</body>
</html>