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
            PreparedStatement ps = conn.prepareStatement("insert into users values (?, ?, ?, ?, ?, ?)");
            ps.setLong(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isAdmin());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUser(User user) {
        try {
//            PreparedStatement ps = conn.prepareStatement("delete from users where id = " + user.getId()
//                    + " and username = " + user.getUsername() + ";");
            PreparedStatement ps = conn.prepareStatement("delete from users where id = ? and username = ?");
            ps.setLong(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        ResultSet rs;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ?");
            ps.setString(1, username);
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
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? and" +
                    "password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
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
            PreparedStatement ps = conn.prepareStatement("select * from quiz_history where user_id = ?");
            ps.setLong(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                UserAttempt tmp = new UserAttempt(rs.getInt("id"), rs.getInt("quiz_id"),
                        rs.getInt("user_id"), rs.getInt("score"), rs.getTimestamp("attempt_time"));
                ans.add(tmp);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    public double getMaxScore(User user) {
        List<UserAttempt> history = this.getHistory(user);
        double maxScore = 0;
        for (UserAttempt ua : history) {
            if (ua.getScore() > maxScore) maxScore = ua.getScore();
        }
        return maxScore;
    }

    public double getMinScore(User user) {
        List<UserAttempt> history = this.getHistory(user);
        double minScore = Double.MAX_VALUE;
        for (UserAttempt ua : history) {
            if (ua.getScore() < minScore) minScore = ua.getScore();
        }
        return minScore;
    }

    public double getAverageScore(User user) {
        List<UserAttempt> history = this.getHistory(user);
        double total = 0;
        for (UserAttempt ua : history) {
            total += ua.getScore();
        }
        return total / history.size();
    }

    public void makeAdmin(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("update users set is_admin = true where id = ?");
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
        ps.setLong(1, id);
        ResultSet rs;
        rs = ps.executeQuery();
        return new User(rs.getString("username"), rs.getString("password"), rs.getInt("id"),
                rs.getBoolean("is_admin"), rs.getString("first_name"), rs.getString("last_name"));
    }

    public void changePassword(User user, String hashedPassword) {
        try {
            PreparedStatement ps;
//            if (user.getId() != 0) ps = conn.prepareStatement("update users set password = " + hashedPassword +
//                    " where id = " + user.getId());
//            else if (user.getUsername() != "") ps = conn.prepareStatement("update users set password = " + hashedPassword +
//                    " where username = " + user.getUsername());
            if (user.getId() != 0) {
                ps = conn.prepareStatement("update users set password = ? where id = ?");
                ps.setString(1, hashedPassword);
                ps.setLong(2, user.getId());
                ps.executeUpdate();
            } else if (user.getUsername() != "") {
                ps = conn.prepareStatement("update users set password = ? where username = ?");
                ps.setString(1, hashedPassword);
                ps.setString(2, user.getUsername());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
