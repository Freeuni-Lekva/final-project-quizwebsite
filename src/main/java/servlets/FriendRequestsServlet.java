package servlets;

import DAO.FriendRequestDao;
import DAO.MessageDao;
import DAO.UserDao;
import mailbox.FriendRequest;
import mailbox.Message;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FriendRequestsServlet")
public class FriendRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        FriendRequestDao friendRequestDao = (FriendRequestDao) httpServletRequest.getServletContext().getAttribute("FriendRequestDao");
        UserDao uDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");

        try {
            List<FriendRequest> requests = friendRequestDao.getFriendRequests(user.getId());
            List<User> friendReqs= new ArrayList<>();
            for (FriendRequest request : requests) {
                friendReqs.add(uDao.getUser(request.getFromId()));
            }
            httpServletRequest.setAttribute("reqs", friendReqs);
            httpServletRequest.getRequestDispatcher("friendRequests.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        long id = Long.parseLong(httpServletRequest.getParameter("userId"));
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        UserDao uDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        FriendRequestDao friendRequestDao = (FriendRequestDao) httpServletRequest.getServletContext().getAttribute("FriendRequestDao");
        try {
            if (httpServletRequest.getParameter("accept") != null) {
                uDao.addFriend(user.getId(), id);
                uDao.addFriend(id, user.getId());
            }
            friendRequestDao.removeFriendRequest(new FriendRequest(id, user.getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        httpServletResponse.sendRedirect("/FriendRequestsServlet");
    }
}