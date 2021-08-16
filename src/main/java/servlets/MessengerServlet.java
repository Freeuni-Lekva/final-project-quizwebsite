package servlets;

import DAO.MessageDao;
import DAO.UserDao;
import mailbox.Message;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;


@WebServlet("/MessengerServlet")
public class MessengerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse  httpServletResponse) throws ServletException, IOException {
        MessageDao mDao = (MessageDao) httpServletRequest.getServletContext().getAttribute("MessageDao");
        Message message = new Message(Long.parseLong(httpServletRequest.getParameter("from_user_id")), Long.parseLong(httpServletRequest.getParameter("to_user_id")),
                httpServletRequest.getParameter("msg_text"), new Timestamp(System.currentTimeMillis()));
        try {
            mDao.addMessage(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/MessengerServlet?from_user_id=" + httpServletRequest.getParameter("to_user_id"));
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao userDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        if (httpServletRequest.getSession().getAttribute("currUser") == null) {
            httpServletResponse.sendRedirect("login.jsp");
            return;
        }
        long fromUserId = Long.parseLong(httpServletRequest.getParameter("from_user_id"));
        User user = null;
        try {
            user = userDao.getUser(fromUserId);
            httpServletRequest.setAttribute("user", user);
            httpServletRequest.getRequestDispatcher("messenger.jsp").forward(httpServletRequest,httpServletResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
