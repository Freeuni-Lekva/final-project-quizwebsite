package dao;

import DAO.MessageDao;
import DAO.UserDao;
import database.DatabaseConnection;
import mailbox.Message;
import org.junit.Before;
import org.junit.Test;
import user.Hash;
import user.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMessageDao {
    User user1;
    User user2;
    UserDao dao1;
    MessageDao dao;
    Message msg;

    @Before
    public void init() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException, IOException {
        DatabaseConnection.resetTables();
        user1 = new User("test1", new Hash("pass").hashPassword(), false, "name1", "last1");
        user2 = new User("test2", new Hash("pass").hashPassword(), false, "name2", "last2");
        dao1 = new UserDao(DatabaseConnection.getConnection());
        dao1.addUser(user1);
        dao1.addUser(user2);
        msg = new Message(user1.getId(), user2.getId(), "what's up", new Timestamp(System.currentTimeMillis()));
        dao = new MessageDao(DatabaseConnection.getConnection());
        dao.addMessage(msg);
        dao.addMessage(new Message(user1.getId(), user2.getId(), "what's up??", new Timestamp(System.currentTimeMillis())));
    }

    @Test
    public void testGetConversation() throws SQLException {
        assertEquals(2, dao.getConversation(user1.getId(), user2.getId()).size());
        dao.addMessage(new Message(user2.getId(), user1.getId(), "test", new Timestamp(System.currentTimeMillis())));
        assertEquals(3, dao.getConversation(user1.getId(), user2.getId()).size());
    }
}
