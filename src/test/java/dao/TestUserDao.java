package dao;

import DAO.QuizDao;
import DAO.UserDao;
import database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import question.Question;
import quiz.Quiz;
import quiz.QuizAttempt;
import quiz.RandomOrderQuiz;
import user.Hash;
import user.User;
import user.UserAttempt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUserDao {
    User user1;
    User user2;
    Quiz quiz;
    QuizDao quizDao;
    UserDao dao;
    UserAttempt attempt;

    @Before
    public void init() throws NoSuchAlgorithmException, SQLException, IOException, ClassNotFoundException {
        DatabaseConnection.resetTables();
        user1 = new User("test1", new Hash("pass").hashPassword(), false, "testName1", "testLastName1");
        user2 = new User("test2", new Hash("pass").hashPassword(), false, "testName2", "testLastName2");
        dao = new UserDao(DatabaseConnection.getConnection());
        dao.addUser(user1);
        dao.addUser(user2);
        quiz = new RandomOrderQuiz(new ArrayList<Question>(), user1, "quiz1", new ArrayList<QuizAttempt>());
        quizDao = new QuizDao(DatabaseConnection.getConnection());
        quizDao.addQuiz(quiz);
        attempt = new UserAttempt(quiz.getId(), user1.getId(), 32.2, new Timestamp(System.currentTimeMillis()));
        dao.addAttempt(attempt);
    }

    @Test
    public void testGetAdd() throws SQLException {
        User tmpUser = dao.getUser(user1.getId());
        assertEquals(user1.getId(), tmpUser.getId());
        assertEquals(user1.getUsername(), tmpUser.getUsername());
        dao.removeUser(user2.getId());
    }

    @Test
    public void getFriends() throws SQLException {
        dao.addFriend(user1.getId(), user2.getId());
        List<User> friendList = dao.getFriends(user1.getId());
        assertEquals(1, friendList.size());
        assertEquals(user2.getId(), friendList.get(0).getId());
    }

    @Test
    public void testGetAttempts() throws SQLException {
        List<UserAttempt> list = dao.getAttempts(user1.getId());
        assertEquals(1, list.size());
        assertEquals(attempt.getScore(), list.get(0).getScore(), 0.01);
    }

    @Test
    public void testMakeAdmin() throws SQLException {
        dao.makeAdmin(user1.getId());
        assertTrue(dao.getUser(user1.getId()).isAdmin());
    }

    @Test(expected = Exception.class)
    public void testRemove() throws SQLException {
        dao.removeUser(user2.getId());
        dao.getUser(user2.getId());
    }
}
