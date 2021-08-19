package servlets;

import DAO.*;
import database.DatabaseConnection;
import mailbox.FriendRequest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class ContextSessionListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        UserDao userDao = null;
        QuizDao quizDao = null;
        MessageDao messageDao = null;
        FriendRequestDao friendRequestDao = null;

        try {
            userDao = new UserDao(DatabaseConnection.getConnection());
            quizDao = new QuizDao(DatabaseConnection.getConnection());
            messageDao = new MessageDao(DatabaseConnection.getConnection());
            friendRequestDao = new FriendRequestDao(DatabaseConnection.getConnection());


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        servletContextEvent.getServletContext().setAttribute("index", 0);
        servletContextEvent.getServletContext().setAttribute("UserDao", userDao);
        servletContextEvent.getServletContext().setAttribute("QuizDao", quizDao);
        servletContextEvent.getServletContext().setAttribute("MessageDao", messageDao);
        servletContextEvent.getServletContext().setAttribute("FriendRequestDao", friendRequestDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("currUser", null);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("currUser", null);
    }
}
