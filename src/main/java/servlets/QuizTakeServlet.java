package servlets;

import DAO.*;
import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import quiz.QuizAttempt;
import quiz.RandomOrderQuiz;
import response.MultipleOrderedAnswerResponse;
import response.MultipleUnorderedAnswerResponse;
import response.Response;
import user.User;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

@WebServlet("/QuizTakeServlet")
public class QuizTakeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        Timestamp timestamp = new Timestamp(currentTimeMillis());
        long userId = ((User)httpServletRequest.getSession().getAttribute("currUser")).getId();
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        Quiz quiz = null;
        try {
            quiz = quizDao.getQuiz(quizId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        List<Question> questionList = quiz.getQuestions();
        double score = 0;

        for (int i = 0; i < questionList.size(); i++) {
            String[] values = httpServletRequest.getParameterValues(String.valueOf(i));
            HashSet<String> answers = new HashSet<>();
            for (String val : values) {
                if (val != null) {
                    answers.add(val);
                }
            }
            score += questionList.get(i).getScore(new MultipleUnorderedAnswerResponse(answers));
        }

        UserAttempt att= new UserAttempt(quizId, userId, score, timestamp);
        try {
            UserDao uDao = new UserDao(DatabaseConnection.getConnection());
            uDao.addAttempt(att);
            httpServletRequest.setAttribute("user", uDao.getUser(userId));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        httpServletRequest.setAttribute("maxscore", questionList.size());
        httpServletRequest.setAttribute("score", score);
        httpServletRequest.getRequestDispatcher("result.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        try {
            Quiz quiz = quizDao.getQuiz(quizId);
            httpServletRequest.setAttribute("questionList", quiz.getQuestions());
            httpServletRequest.setAttribute("quizId", quizId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        httpServletRequest.getRequestDispatcher("quizTake.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
