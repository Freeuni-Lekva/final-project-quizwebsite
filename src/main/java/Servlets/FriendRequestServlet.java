package Servlets;

import DAO.FriendshipDao;
import mailbox.FriendRequest;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FriendRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        String currUser = request.getParameter("curr-user");

        FriendshipDao friendshipDao = (FriendshipDao) request.getServletContext().getAttribute("friendshipDao");
        if (request.getParameter("send-friend-request") != null) {
            try {
                friendshipDao.addRequest(username, currUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (request.getParameter("accept") != null) {
            try {
                friendshipDao.removeRequest(username, currUser);
                friendshipDao.addFriends(username, currUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (request.getParameter("reject") != null) {
            try {
                friendshipDao.removeRequest(username, currUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (request.getParameter("remove-friend") != null) {
            try {
                friendshipDao.removeFriends(username, currUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.getRequestDispatcher("ProfileServlet?username=" + currUser).forward(request, response);
    }
}
