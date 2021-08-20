package servlets;

import DAO.QuizDao;
import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import quiz.RandomOrderQuiz;
import quiz.StandardQuiz;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.MatrixParam;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<Question> questions =(List<Question>) httpServletRequest.getSession().getAttribute("questions");
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        int curr =  (int)httpServletRequest.getSession().getAttribute("currQuestions");
        int  n = (int) httpServletRequest.getSession().getAttribute("nQuestions");
        String type = (String)httpServletRequest.getSession().getAttribute("type");

        String text ;
        int nRequestedAnswers ;
        int nLegalAnswers;
        String image;
        String rightAns;
        int nChoices;

        String[] ans =httpServletRequest.getParameterValues("answer");
        HashSet<String> answers;
        if(ans!=null){
            answers = new HashSet<>();
            answers.addAll(List.of(ans));
            text = (String) httpServletRequest.getSession().getAttribute("qText");
            switch (type){
                case  "MultipleAnswerUnorderedResponseQuestion" :
                        nRequestedAnswers = (int) httpServletRequest.getSession().getAttribute("nAnswers");
                        MultipleAnswerUnorderedResponseQuestion q = new MultipleAnswerUnorderedResponseQuestion(text, answers, nRequestedAnswers);
                        questions.add(q);
                        break;
                case  "MultipleChoiceUnorderedResponseQuestion" :
                        rightAns = (String) httpServletRequest.getSession().getAttribute("rightAnswer");
                        HashSet<String> ch = new HashSet<>();
                                  ch.add(rightAns);
                        answers.add(rightAns);
                        MultipleChoiceUnorderedResponseQuestion q1= new MultipleChoiceUnorderedResponseQuestion(text,  ch, answers);
                        questions.add(q1);
                        break;
                case   "PictureUnorderedResponseQuestion":
                        image = (String) httpServletRequest.getSession().getAttribute("image");
                        PictureUnorderedResponseQuestion q2 = new PictureUnorderedResponseQuestion(text, answers, image);
                        questions.add(q2);
                        break;
                case   "StandardUnorderedResponseQuestion":
                        StandardUnorderedResponseQuestion q3 = new StandardUnorderedResponseQuestion(text, answers);
                        questions.add(q3);
                        break;
            }

            httpServletRequest.getSession().setAttribute("questions", questions);
            if(curr==n){
                addQuiz(httpServletRequest, httpServletResponse);
                httpServletRequest.setAttribute("user", user);
                httpServletRequest.getSession().setAttribute("questions", null);
                httpServletResponse.sendRedirect( "/UserServlet?username=" +user.getUsername());
            } else {
                httpServletRequest.getRequestDispatcher("questionTypes.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } else {
            if(type.equals("MultipleAnswerUnorderedResponseQuestion")) {
                nRequestedAnswers = (int) Long.parseLong(httpServletRequest.getParameter("nAnswers"));
                httpServletRequest.getSession().setAttribute("nAnswers", nRequestedAnswers);
            }
            if(type.equals("PictureUnorderedResponseQuestion")){
                image =  httpServletRequest.getParameter("image");
                httpServletRequest.getSession().setAttribute("image", image);
            }
            if(type.equals("MultipleChoiceUnorderedResponseQuestion")) {
                rightAns = httpServletRequest.getParameter("rightAnswer");
                nChoices = (int) Long.parseLong(httpServletRequest.getParameter("nChoices"));
                httpServletRequest.getSession().setAttribute("nChoices", nChoices);
                httpServletRequest.setAttribute("nLegalAnswers", nChoices-1);
                httpServletRequest.getSession().setAttribute("rightAnswer", rightAns);
            } else {
                nLegalAnswers = (int) Long.parseLong(httpServletRequest.getParameter("nLegalAnswers"));
                httpServletRequest.setAttribute("nLegalAnswers", nLegalAnswers);
            }
            httpServletRequest.setAttribute("type", type);
            text = httpServletRequest.getParameter("qText");
            httpServletRequest.getSession().setAttribute("qText", text);
            httpServletRequest.getRequestDispatcher("getAnswers.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    private void addQuiz(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Boolean isRandom = (Boolean) httpServletRequest.getSession().getAttribute("isRandom");
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        String quizName = (String) httpServletRequest.getSession().getAttribute("name");
        List<Question> questions =(List<Question>) httpServletRequest.getSession().getAttribute("questions");
        Quiz quiz;
        if(isRandom){
            quiz = new RandomOrderQuiz(questions, user, quizName, null );
        } else {
            quiz = new StandardQuiz(questions, user, quizName, null );
        }
        try {
            QuizDao qDao = new QuizDao(DatabaseConnection.getConnection());
            qDao.addQuiz(quiz);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
