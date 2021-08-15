package DAO;

import database.DatabaseConnection;
import quiz.Quiz;
import user.User;
import user.UserAttempt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into users (username, hashed_password, is_admin, first_name, last_name) values (?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setBoolean(3, user.isAdmin());
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        user.setId(id);
    }

    public void removeUser(long userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from users where id = ?");
        ps.setLong(1, userId);
        ps.executeUpdate();
    }

    public User getUser(long userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from users where id = ?");
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new User(rs.getLong("id"), rs.getString("username"), rs.getString("hashed_password"),
                rs.getBoolean("is_admin"), rs.getString("first_name"), rs.getString("last_name"));
    }

    public void makeAdmin(long userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update users set is_admin = true where id = ?");
        ps.setLong(1, userId);
        ps.executeUpdate();
    }

    public List<UserAttempt> getAttempts(long userId) throws SQLException {
        List<UserAttempt> result = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from quiz_history where user_id = ?");
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            result.add(new UserAttempt(rs.getLong("id"), rs.getLong("quiz_id"), userId,
                    rs.getDouble("score"), rs.getTimestamp("attempt_time")));
        }
        return result;
    }

    public List<User> getFriends(long userId) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from friendship where first_user_id = ?");
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long friend_id = rs.getLong("second_user_id");
            PreparedStatement ps1 = connection.prepareStatement("select * from users where id = ?");
            ps1.setLong(1, friend_id);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()) {
                result.add(new User(friend_id, rs1.getString("username"), rs1.getString("hashed_password"),
                        rs1.getBoolean("is_admin"), rs1.getString("first_name"), rs1.getString("last_name")));
            }
        }
        return result;
    }

    public void addFriend(long firstUserId, long secondUserId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into friendship (first_user_id, second_user_id) values (?, ?)");
        ps.setLong(1, firstUserId);
        ps.setLong(2, secondUserId);
        ps.executeUpdate();
        ps = connection.prepareStatement("insert into friendship (first_user_id, second_user_id) values (?, ?)");
        ps.setLong(1, secondUserId);
        ps.setLong(2, firstUserId);
    }

    public void addAttempt(UserAttempt attempt) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into quiz_history(quiz_id, user_id, score, attempt_time) values (?, ?, ?, ?)");
        ps.setLong(1, attempt.getQuizId());
        ps.setLong(2, attempt.getUserId());
        ps.setDouble(3, attempt.getScore());
        ps.setTimestamp(4, attempt.getTimeStamp());
        ps.executeUpdate();
    }
    public User getUser(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from users where username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return new User(rs.getLong("id"), rs.getString("username"), rs.getString("hashed_password"),
                    rs.getBoolean("is_admin"), rs.getString("first_name"), rs.getString("last_name"));
        }
        return null;
    }

    public List<Quiz> getCreatedQuizzes(long userId) throws SQLException, ClassNotFoundException {
        QuizDao dao = new QuizDao(DatabaseConnection.getConnection());
        PreparedStatement ps = connection.prepareStatement("select * from quizzes where author = ?");
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        List<Quiz> list = new ArrayList<>();
        while(rs.next()) {
            list.add(dao.getQuiz(rs.getLong("id")));
        }
        return list;
    }
}
