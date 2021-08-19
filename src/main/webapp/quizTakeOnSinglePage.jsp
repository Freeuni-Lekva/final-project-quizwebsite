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
                List<Question> questions = (List<Question>)session.getAttribute("questions");
                if(questions==null){
                      questions =  (List<Question>)request.getAttribute("questions");
                      session.setAttribute("questions", questions);
                }
                Double score = (Double)request.getAttribute("score");
                if(score==null){
                      score =0.0;
                }
                 MultipleAnswerUnorderedResponseQuestionDao qDao1= new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                 MultipleChoiceUnorderedResponseQuestionDao qDao2= new MultipleChoiceUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                 PictureUnorderedResponseQuestionDao qDao3 = new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                 StandardUnorderedResponseQuestionDao qDao4 = new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());

                 List<MultipleAnswerUnorderedResponseQuestion> q1 = qDao1.getQuestionsMultipleAnsUnordered(qId);
                 List<MultipleChoiceUnorderedResponseQuestion> q2 = qDao2.getQuestionsWithChoices(qId);
                 List<PictureUnorderedResponseQuestion> q3 = qDao3.getQuestionsPictureUnordered(qId);
                 List<StandardUnorderedResponseQuestion> q4 = qDao4.getQuestionsStandardUnordered(qId);

    %>
<section class="vh-100" style="background-color: #508bfc;">
    <div class="container py-5 h-100">

                         <form action='QuizTakeOnSinglePageServlet' method='post'>
                                 <div class="row d-flex justify-content-center align-items-center h-100">

                                     <div class="col-12 col-md-8 col-lg-6 col-xl-5">

                         <div class="card shadow-2-strong" style="border-radius: 1rem;">
                            <%for (int b=0; b<questions.size(); b++) {
                                      Question q = questions.get(b);%>
                                       <label><%=b%>. <%=q.getQuestionText()%></label>
                                            <%for(int a=0; a<q1.size(); a++){
                                                if (q1.get(a).getQuestionText().equals(q.getQuestionText())){
                                                    MultipleAnswerUnorderedResponseQuestion question1 = q1.get(a);
                                                    long n = question1.getNumOfRequestedAnswers();%>
                                                        <% for(int i=0; i<(int)n; i++){%>
                                                            <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <%}%>
                                                        <input type="hidden" name="type" value=1>
                                               <%}
                                            }%>
                                            <%for(int k=0; k<q2.size(); k++){
                                                if (q2.get(k).getQuestionText().equals(q.getQuestionText())){
                                                    HashSet<String> choices= ((MultipleChoiceUnorderedResponseQuestion)q).getChoices();%>
                                                            <%for (String s : choices){%>
                                                                <div id='block-11' style='padding: 10px;'>
                                                                 <label for="answer" >
                                                                    <input type='radio' name='answer' value=<%=s%> id='answer' style='transform: scale(1.6); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />
                                                                 <%=s%></label><br>
                                                                 </div>
                                                            <%}%>
                                                        <input type="hidden" name="type" value=2>
                                               <%}
                                            }%>
                                            <%for(int k=0; k<q3.size(); k++){
                                                if (q3.get(k).getQuestionText().equals(q.getQuestionText())){%>
                                                        <img src=<%=((PictureUnorderedResponseQuestion)q).getPicUrl()%> class='card-img-top'>
                                                        <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <input type="hidden" name="type" value=3>
                                               <%}
                                            }%>
                                            <%for(int k=0; k<q4.size(); k++){
                                                if (q4.get(k).getQuestionText().equals(q.getQuestionText())){%>
                                                        <input required type="text" name="answer" class="form-control form-control-lg mb-4" placeholder="Answer" />
                                                        <input type="hidden" name="type" value=4>
                                               <%}
                                            }%>

                                                        <input type="hidden" name="quizId" value=<%=qId%>>
                                                        <input type='hidden' name='score' value=<%=score%>>


                            <%}%>
                                   <input class="btn btn-success" type="submit" value="Submit">

                            </div>
                           </div>
                        </div>
                        </form>



        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


</body>
</html>