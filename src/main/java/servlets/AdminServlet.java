package servlets;

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
        long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
        User user = null;
        try {
            user = userDao.getUser(userId);
            if (httpServletRequest.getParameter("makeAdmin") != null) {
                userDao.makeAdmin(userId);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/UserServlet?username=" + user.getUsername());
            } else if (httpServletRequest.getParameter("deleteUser") != null) {
                userDao.removeUser(userId);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/UserServlet?username=" +
                        ((User)httpServletRequest.getSession().getAttribute("currUser")).getUsername());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
