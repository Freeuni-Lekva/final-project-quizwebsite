package servlets;

import DAO.UserDao;
import quiz.Quiz;
import user.Hash;
import user.User;
import user.UserAttempt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDao dao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        try {
            User user = dao.getUser(username);
            List<User> friendList = dao.getFriends(user.getId());
            List<Quiz> createdQuizzes = dao.getCreatedQuizzes(user.getId());
            List<UserAttempt> attempts = dao.getAttempts(user.getId());
            if (user.getPassword().equals(new Hash(password).hashPassword())) {
                httpServletRequest.getSession().setAttribute("currUser", user);
                httpServletRequest.setAttribute("user", user);
                httpServletRequest.setAttribute("friendList", friendList);
                httpServletRequest.setAttribute("createdQuizzes", createdQuizzes);
                httpServletRequest.setAttribute("attempts", attempts);
                httpServletRequest.getRequestDispatcher("myProfile.jsp").forward(httpServletRequest, httpServletResponse);
            } else {
                httpServletRequest.setAttribute("text", "Username or password is incorrect, try again.");
                httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } catch (NoSuchAlgorithmException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
