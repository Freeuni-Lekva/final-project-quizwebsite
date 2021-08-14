package servlets;

import DAO.UserDao;
import user.Hash;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao dao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        try {
            User user = dao.getUser(username);
            if (user != null && user.getPassword().equals(new Hash(password).hashPassword())) {
                httpServletRequest.getSession().setAttribute("currUser", user);
                httpServletRequest.getRequestDispatcher("welcome.jsp").forward(httpServletRequest, httpServletResponse);
            } else {
                httpServletRequest.setAttribute("text", "Username or password is incorrect, try again.");
                httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }
}
