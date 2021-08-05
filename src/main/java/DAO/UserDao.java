package DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.apache.commons.dbcp2.BasicDataSource;
import user.User;
import user.UserAttempt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection conn;

    public UserDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/quizwebsite_db");
        dataSource.setUsername("root");
        dataSource.setPassword("Alex.2001");
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into users (id, username, hashed_password," +
                    " is_admin, first_name, last name) values (" + user.getId() + ", " + user.getUsername() + ", "
                    + user.getPassword() + ", " + user.isAdmin() + ", " + user.getFirstName() + ", " + user.getLastName()
                    + ");");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUser(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from users where id = " + user.getId()
                    + " and username = " + user.getUsername() + ";");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        ResultSet rs;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where username = " + username);
            rs = ps.executeQuery();
            if (rs.next()) return true;
            else return false;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean isPasswordCorrect(String username, String password) {
        ResultSet rs;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where username = " + username +" and" +
                    "password = " + password);
            rs = ps.executeQuery();
            if (rs.next()) return true;
            else return false;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public List<UserAttempt> getHistory(User user) {
        ResultSet rs;
        List<UserAttempt> ans = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from quiz_history where user_id = " + user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                UserAttempt tmp = new UserAttempt(rs.getInt("id"), rs.getInt("quiz_id"),
                        rs.getInt("user_id"), rs.getInt("score"), rs.getDate("attempt_time"));
                ans.add(tmp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    public void makeAdmin(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("update users set is_admin = true where id = " + user.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changePassword(User user, String hashedPassword) {
        try {
            PreparedStatement ps;
            if (user.getId() != 0) ps = conn.prepareStatement("update users set password = " + hashedPassword +
                    " where id = " + user.getId());
            else if (user.getUsername() != "") ps = conn.prepareStatement("update users set password = " + hashedPassword +
                    " where username = " + user.getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
