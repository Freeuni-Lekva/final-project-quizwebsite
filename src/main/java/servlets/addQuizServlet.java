package servlets;

import DAO.UserDao;
import database.DatabaseConnection;
import question.Question;
import quiz.Quiz;
import user.User;
import user.UserAttempt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addQuizServlet")
public class addQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendRedirect("addQuiz.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String quizName = httpServletRequest.getParameter("name");
        String random = httpServletRequest.getParameter("random");
        Boolean isRandom = false;
        if(random==null){
            isRandom=true;
        }
        int nQuestions = (int)Long.parseLong(httpServletRequest.getParameter("nQuestions"));

        List<Question> questions = new ArrayList<Question>() ;
        httpServletRequest.getSession().setAttribute("name", quizName);
        httpServletRequest.getSession().setAttribute("questions", questions);
        httpServletRequest.getSession().setAttribute("nQuestions", nQuestions);
        httpServletRequest.getSession().setAttribute("currQuestions", 0);
        httpServletRequest.getSession().setAttribute("isRandom", isRandom);
        httpServletRequest.getRequestDispatcher("questionTypes.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
