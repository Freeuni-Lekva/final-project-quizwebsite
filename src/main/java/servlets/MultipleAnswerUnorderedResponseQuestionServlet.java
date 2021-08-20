package servlets;

import DAO.QuizDao;
import database.DatabaseConnection;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;
import quiz.RandomOrderQuiz;
import quiz.StandardQuiz;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
@WebServlet("/MultipleAnswerUnorderedResponseQuestionServlet")
public class MultipleAnswerUnorderedResponseQuestionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<Question> questions =(List<Question>) httpServletRequest.getSession().getAttribute("questions");
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        int curr =  (int)httpServletRequest.getSession().getAttribute("currQuestions");
        int  n = (int) httpServletRequest.getSession().getAttribute("nQuestions");

        String text ;
        int nRequestedAnswers ;
        int nLegalAnswers;
        String type = "MultipleAnswerUnorderedResponseQuestion";

        String[] ans =httpServletRequest.getParameterValues("answer");
        HashSet<String> answers;
        if(ans!=null){
            answers = new HashSet<>();
            answers.addAll(List.of(ans));
            text = (String) httpServletRequest.getSession().getAttribute("qText");
            nRequestedAnswers = (int) httpServletRequest.getSession().getAttribute("nAnswers");
            MultipleAnswerUnorderedResponseQuestion q = new MultipleAnswerUnorderedResponseQuestion(text, answers, nRequestedAnswers);
            questions.add(q);
            httpServletRequest.getSession().setAttribute("questions", questions);
            if(curr==n){
                addQuiz(httpServletRequest, httpServletResponse);
                httpServletRequest.setAttribute("user", user);
                httpServletRequest.getRequestDispatcher("user.jsp").forward(httpServletRequest, httpServletResponse);
            } else {
                httpServletRequest.getRequestDispatcher("questionTypes.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } else {
            text = httpServletRequest.getParameter("qText");
            nRequestedAnswers =(int) Long.parseLong(httpServletRequest.getParameter("nAnswers"));
            nLegalAnswers = (int) Long.parseLong(httpServletRequest.getParameter("nLegalAnswers"));
            httpServletRequest.setAttribute("type", type);
            httpServletRequest.getSession().setAttribute("qText", text);
            httpServletRequest.getSession().setAttribute("nAnswers", nRequestedAnswers);
            httpServletRequest.setAttribute("nLegalAnswers", nLegalAnswers);
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
