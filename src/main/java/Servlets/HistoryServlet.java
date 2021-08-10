package Servlets;

import DAO.UserDao;
import user.User;
import user.UserAttempt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
        List<UserAttempt> history = userDao.getHistory(user);
        request.setAttribute("history", history);
        request.getRequestDispatcher("full_history.jsp").forward(request, response);
    }
}
