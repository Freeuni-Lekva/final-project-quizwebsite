package servlets;

import DAO.*;
import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import quiz.QuizAttempt;
import quiz.RandomOrderQuiz;
import response.MultipleUnorderedAnswerResponse;
import response.Response;
import user.UserAttempt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

@WebServlet("/QuizTakeServlet")
public class QuizTakeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Timestamp timestamp = new Timestamp(currentTimeMillis());
        long userId =Long.parseLong(httpServletRequest.getParameter("userId"));
        Double score= Double.parseDouble(httpServletRequest.getParameter("score"));
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        UserAttempt att= new UserAttempt(quizId, userId, score, timestamp);
        try {
            UserDao uDao = new UserDao(DatabaseConnection.getConnection());
            uDao.addAttempt(att);
            httpServletRequest.setAttribute("user", uDao.getUser(userId));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        httpServletRequest.getRequestDispatcher("user.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        Boolean isMultipleChoice = Boolean.parseBoolean(httpServletRequest.getParameter("multipleChoice"));
        double score = Double.parseDouble(httpServletRequest.getParameter("score"));
        String[] getAnswers=httpServletRequest.getParameterValues("answer");
        int type = (int)Long.parseLong(httpServletRequest.getParameter("type"));
        int index = -1 ;
        index =  (int)Long.parseLong(httpServletRequest.getParameter("index"));
        List<Question> questions= (List<Question> )httpServletRequest.getSession().getAttribute("questions");
        httpServletRequest.setAttribute("questions", questions);
        if(index!=-1&&index<questions.size()) {
                HashSet<String> ans = new HashSet<>();
                for(int i=0; i<getAnswers.length; i++){
                    ans.add(getAnswers[i]);
                }
                Question que = questions.get(index-1);

            try {
                MultipleAnswerUnorderedResponseQuestionDao qDao1 = new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                MultipleChoiceUnorderedResponseQuestionDao qDao2= new MultipleChoiceUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                PictureUnorderedResponseQuestionDao qDao3 = new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
                StandardUnorderedResponseQuestionDao qDao4 = new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());

                List<MultipleAnswerUnorderedResponseQuestion> q1 = qDao1.getQuestionsMultipleAnsUnordered(quizId);
                List<MultipleChoiceUnorderedResponseQuestion> q2 = qDao2.getQuestionsWithChoices(quizId);
                List<PictureUnorderedResponseQuestion> q3 = qDao3.getQuestionsPictureUnordered(quizId);
                List<StandardUnorderedResponseQuestion> q4 = qDao4.getQuestionsStandardUnordered(quizId);
                MultipleUnorderedAnswerResponse r = new MultipleUnorderedAnswerResponse(ans);

                Double sc =0.0;
                if(type==1){
                    for(int i=0; i<q1.size(); i++){
                        if(q1.get(i).getQuestionText().equals(que.getQuestionText())){
                            sc = q1.get(i).getScore(r);
                        }
                    }
                } else if(type==2){
                    for(int i=0; i<q2.size(); i++){
                        String s = q2.get(i).getQuestionText();
                        if(s.equals(que.getQuestionText())){
                            sc = q2.get(i).getScore(r);

                        }
                    }
                } else if(type==3){
                    for(int i=0; i<q3.size(); i++){
                        if(q3.get(i).getQuestionText().equals(que.getQuestionText())){
                            sc = q3.get(i).getScore(r);

                        }
                    }
                } else  if(type==4){
                    for(int i=0; i<q4.size(); i++){
                        if(q4.get(i).getQuestionText().equals(que.getQuestionText())){
                            sc = q4.get(i).getScore(r);
                        }
                    }
                }
                System.out.println(sc);
                httpServletRequest.setAttribute("score", sc + score);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        httpServletRequest.getRequestDispatcher("quizTake.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
