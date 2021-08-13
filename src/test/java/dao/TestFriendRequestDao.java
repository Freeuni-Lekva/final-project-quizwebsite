package dao;

import DAO.FriendRequestDao;
import DAO.UserDao;
import database.DatabaseConnection;
import mailbox.FriendRequest;
import org.junit.Before;
import org.junit.Test;
import user.Hash;
import user.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestFriendRequestDao {
    User user1;
    User user2;
    UserDao dao1;
    FriendRequestDao dao;
    FriendRequest request;

    @Before
    public void init() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException, IOException {
        DatabaseConnection.resetTables();
        user1 = new User("test1", new Hash("pass").hashPassword(), false, "name1", "last1");
        user2 = new User("test2", new Hash("pass").hashPassword(), false, "name2", "last2");
        dao1 = new UserDao(DatabaseConnection.getConnection());
        dao1.addUser(user1);
        dao1.addUser(user2);
        request = new FriendRequest(user1.getId(), user2.getId());
        dao = new FriendRequestDao(DatabaseConnection.getConnection());
        dao.addFriendRequest(request);
    }

    @Test
    public void testGetFriendRequest() throws SQLException {
        assertEquals(1, dao.getFriendRequests(user2.getId()).size());
        assertEquals(0, dao.getFriendRequests(user1.getId()).size());
    }

    @Test
    public void testRemoveFriendRequest() throws SQLException {
        dao.removeFriendRequest(request);
        assertEquals(0, dao.getFriendRequests(user2.getId()).size());
        assertEquals(0, dao.getFriendRequests(user1.getId()).size());
    }
}
