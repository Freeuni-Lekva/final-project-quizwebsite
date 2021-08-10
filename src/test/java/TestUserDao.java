import DAO.UserDao;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import user.Hash;
import user.User;
import user.UserAttempt;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestUserDao extends TestCase {

    private Connection conn;
    private Hash hash;

    public TestUserDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/quizwebsite_db");
        dataSource.setUsername("root");
        dataSource.setPassword("Alex.2001");
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        hash = new Hash();
    }

    public void testAdd() throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hash.hashPassword("password");
        User user = new User("username", hashedPassword, 4,
                false, "Aleksandre", "Naneishvili");
        UserDao dao = new UserDao();
        dao.addUser(user);
        ResultSet rs;
        PreparedStatement ps = conn.prepareStatement("select * from users where username = " + user.getUsername() +
                " and id = " + user.getId());
        rs = ps.executeQuery();
        assertTrue(rs.next());
    }

    public void testExistsAndPasswordCorrect() throws NoSuchAlgorithmException, SQLException {
        String hashedPassword = hash.hashPassword("password");
        UserDao dao = new UserDao();
        assertTrue(dao.userExists("username"));
        assertTrue(dao.isPasswordCorrect("username", hashedPassword));
    }

    public void testMakeAdmin() throws NoSuchAlgorithmException, SQLException {
        String hashedPassword = hash.hashPassword("password");
        User user = new User("username", hashedPassword, 4,
                false, "Aleksandre", "Naneishvili");
        UserDao dao = new UserDao();
        dao.makeAdmin(user);
        ResultSet rs;
        PreparedStatement ps = conn.prepareStatement("select * from users where id = " + user.getId());
        rs = ps.executeQuery();
        boolean b = rs.getBoolean("is_admin");
        assertFalse(b);
    }



    public void testRemove() throws NoSuchAlgorithmException, SQLException {
        String hashedPassword = hash.hashPassword("password");
        User user = new User("username", hashedPassword, 4,
                false, "Aleksandre", "Naneishvili");
        UserDao dao = new UserDao();
        dao.removeUser(user);
        ResultSet rs;
        PreparedStatement ps = conn.prepareStatement("select * from users where username = " + user.getUsername() +
                " and id = " + user.getId());
        rs = ps.executeQuery();
        assertTrue(rs.next());
    }

}
