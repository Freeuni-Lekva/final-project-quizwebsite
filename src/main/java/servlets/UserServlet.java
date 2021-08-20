package servlets;

import DAO.FriendRequestDao;
import DAO.QuizDao;
import DAO.UserDao;
import database.DatabaseConnection;
import mailbox.FriendRequest;
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

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao userDao = null;
        List<User> friendList = null;
        List<Quiz> createdQuizzes = null;
        List<UserAttempt> attempts = null;
        try {
            userDao = new UserDao(DatabaseConnection.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        User user = null;
        try {
            user = userDao.getUser(httpServletRequest.getParameter("username"));
            if (user != null) {
                friendList = userDao.getFriends(user.getId());
                createdQuizzes = userDao.getCreatedQuizzes(user.getId());
                attempts = userDao.getAttempts(user.getId());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (user == null) {
            httpServletResponse.sendRedirect("pageNotFound.jsp");
        } else {
            httpServletRequest.setAttribute("user", user);
            httpServletRequest.setAttribute("friendList", friendList);
            httpServletRequest.setAttribute("createdQuizzes", createdQuizzes);
            httpServletRequest.setAttribute("attempts", attempts);
            httpServletRequest.getRequestDispatcher("user.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        FriendRequestDao dao = (FriendRequestDao) httpServletRequest.getServletContext().getAttribute("FriendRequestDao");
        long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
        String username = httpServletRequest.getParameter("username");
        User currUser = (User)httpServletRequest.getSession().getAttribute("currUser");
        if (httpServletRequest.getParameter("sendReq") != null) {
            try {
                dao.addFriendRequest(new FriendRequest(currUser.getId(), userId));
                httpServletResponse.sendRedirect("/UserServlet?username=" + username);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (httpServletRequest.getParameter("respondReq") != null) {
            httpServletResponse.sendRedirect("/FriendRequestsServlet?userId=" + userId);
        }
    }
}
