package servlets;

import DAO.QuizDao;
import DAO.UserDao;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao userDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        QuizDao quizDao = (QuizDao) httpServletRequest.getServletContext().getAttribute("QuizDao");
        User user = null;
        try {
            if (httpServletRequest.getParameter("makeAdmin") != null) {
                long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
                user = userDao.getUser(userId);
                userDao.makeAdmin(userId);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/UserServlet?username=" + user.getUsername());
            } else if (httpServletRequest.getParameter("deleteUser") != null) {
                long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
                user = userDao.getUser(userId);
                userDao.removeUser(userId);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/UserServlet?username=" +
                        ((User)httpServletRequest.getSession().getAttribute("currUser")).getUsername());
            } else if (httpServletRequest.getParameter("deleteQuiz") != null) {
                quizDao.removeQuiz(Long.parseLong(httpServletRequest.getParameter("quizId")));
                httpServletResponse.sendRedirect("quizzes.jsp");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
