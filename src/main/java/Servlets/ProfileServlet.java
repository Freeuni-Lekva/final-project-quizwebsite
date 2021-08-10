package Servlets;

import DAO.FriendshipDao;
import DAO.UserDao;
import user.User;
import user.UserAttempt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String currUsername = request.getParameter("username");


        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
        List<UserAttempt> history = userDao.getHistory(user);
        request.setAttribute("history", history);
        FriendshipDao friendshipDao = (FriendshipDao) getServletContext().getAttribute("friendshipDao");

        int friendStatus;
        if (user.getUsername().equals(currUsername)) friendStatus = -1;
        else if (friendshipDao.isPending(user.getUsername(), currUsername)) friendStatus = 1;
        else if (friendshipDao.isPending(currUsername, user.getUsername())) friendStatus = 2;
        else if (friendshipDao.areFriends(user.getUsername(), currUsername)) friendStatus = 3;
        else friendStatus = 0;

        request.setAttribute("friendStatus", friendStatus);
        request.getRequestDispatcher("profile.jsp?username=" + currUsername).forward(request, response);
    }
}
