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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao dao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String firstname = httpServletRequest.getParameter("firstname");
        String lastname = httpServletRequest.getParameter("lastname");

        try {
            User user = dao.getUser(username);
            if (user != null) {
                httpServletRequest.getSession().setAttribute("currUser", user);
                httpServletRequest.setAttribute("text", "User already exists, try different username.");
                httpServletRequest.getRequestDispatcher("register.jsp").forward(httpServletRequest, httpServletResponse);
            } else {
                dao.addUser(new User(username, new Hash(password).hashPassword(), false, firstname, lastname));
                httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }
}
