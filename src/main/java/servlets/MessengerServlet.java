package servlets;

import DAO.MessageDao;
import DAO.UserDao;
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
import java.util.List;


@WebServlet("/MessengerServlet")
public class MessengerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse  httpServletResponse) throws ServletException, IOException {
        MessageDao mDao = (MessageDao) httpServletRequest.getServletContext().getAttribute("MessageDao");
        String userName2 =httpServletRequest.getParameter("user_name2");
        UserDao uDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        User user1 = (User) httpServletRequest.getServletContext().getAttribute("currUser");
        try {
            User user2 = uDao.getUser(userName2);
            if(userName2!=null) {
                httpServletRequest.getSession().setAttribute("userName2", userName2);
                List<Message> m =mDao.getConversation(user1.getId(), user2.getId());
                httpServletRequest.getRequestDispatcher("WEB-INF/messenger.jsp").forward(httpServletRequest, (ServletResponse) httpServletRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        MessageDao mDao = (MessageDao) httpServletRequest.getServletContext().getAttribute("MessageDao");
        User user1= (User) httpServletRequest.getServletContext().getAttribute("currUser");
        String userName2 = (String) httpServletRequest.getServletContext().getAttribute("userName2" );
        String text = httpServletRequest.getParameter("message_input");
        UserDao uDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");
        try {
            User user2 = uDao.getUser(userName2);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Message mess= new Message(user1.getId(), user2.getId(), text, timestamp);
            mDao.addMessage(mess);
            httpServletRequest.getRequestDispatcher("WEB-INF/messenger.jsp").forward(httpServletRequest, (ServletResponse) httpServletRequest);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
