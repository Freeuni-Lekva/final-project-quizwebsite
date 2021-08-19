package servlets;

import DAO.QuizDao;
import quiz.Quiz;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        try {
            Quiz quiz = quizDao.getQuiz(quizId);
            httpServletRequest.setAttribute("quiz", quiz);
            httpServletRequest.getRequestDispatcher("quiz.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    protected  void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        try {
            Quiz quiz = quizDao.getQuiz(quizId);
            httpServletRequest.setAttribute("quiz", quiz);
            httpServletRequest.setAttribute("questions", quiz.getQuestions());
            httpServletRequest.getRequestDispatcher("quizTake.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
