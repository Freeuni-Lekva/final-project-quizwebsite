package DAO;

import mailbox.FriendRequest;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipDao {

    private Connection conn;

    public FriendshipDao() {
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

    public boolean areFriends(String first, String second) {
        int firstId = 0, secondId = 0;
        ResultSet rs1, rs2, rs3 = null, rs4 = null;
        try {
            PreparedStatement ps1 = conn.prepareStatement("select * from users where username = ?");
            ps1.setString(1, first);
            rs1 = ps1.executeQuery();
            firstId = rs1.getInt("id");
            ps1.setString(1, second);
            rs2 = ps1.executeQuery();
            secondId = rs2.getInt("id");
            PreparedStatement ps2 = conn.prepareStatement("select * from friendship where first_user_id = ? and" +
                    "second_user_id = ?");
            ps2.setInt(1, firstId);
            ps2.setInt(2, secondId);
            rs3 = ps2.executeQuery();
            ps2.setInt(1, secondId);
            ps2.setInt(2, firstId);
            rs4 = ps2.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            return rs3.next() || rs4.next();
        } catch (SQLException throwables) {
            return false;
        }
    }

    public void addRequest(String from, String to) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into friend_requests(from_user_id, to_user_id) values (?, ?)");
        ResultSet rs;
        PreparedStatement ps2 = conn.prepareStatement("select * from users where username = ?");
        ps2.setString(1, from);
        rs = ps2.executeQuery();
        int fromId = 0;
        int toId = 0;
        fromId = rs.getInt("id");
        ps2.setString(1, to);
        rs = ps2.executeQuery();
        toId = rs.getInt("id");
        FriendRequest fr = new FriendRequest(fromId, toId);
        ps.setInt(2, fr.getFromId());
        ps.setInt(3, fr.getToId());
        ps.executeUpdate();
    }

    public void removeRequest(String from, String to) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from friend_requests where from_user_id = ? and to_user_id = ?");
        ResultSet rs;
        PreparedStatement ps2 = conn.prepareStatement("select * from users where username = ?");
        ps2.setString(1, from);
        rs = ps2.executeQuery();
        int fromId = 0;
        int toId = 0;
        fromId = rs.getInt("id");
        ps2.setString(1, to);
        rs = ps2.executeQuery();
        toId = rs.getInt("id");
        FriendRequest fr = new FriendRequest(fromId, toId);
        ps.setInt(1, fr.getFromId());
        ps.setInt(2, fr.getToId());
        ps.executeUpdate();
    }

    public void addFriends(String first, String second) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into friendship(first_user_id, second_user_id) values(?, ?)");
        ResultSet rs;
        PreparedStatement ps2 = conn.prepareStatement("select * from users where username = ?");
        ps2.setString(1, first);
        rs = ps2.executeQuery();
        int firstId = 0;
        int secondId = 0;
        firstId = rs.getInt("id");
        ps2.setString(1, second);
        rs = ps2.executeQuery();
        secondId = rs.getInt("id");
        ps.setInt(1, firstId);
        ps.setInt(2, secondId);
        ps.executeUpdate();
    }

    public void removeFriends(String first, String second) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from friendship where first_user_id = ? and second_user_id = ?");
        ResultSet rs;
        PreparedStatement ps2 = conn.prepareStatement("select * from users where username = ?");
        ps2.setString(1, first);
        rs = ps2.executeQuery();
        int firstId = 0;
        int secondId = 0;
        firstId = rs.getInt("id");
        ps2.setString(1, second);
        rs = ps2.executeQuery();
        secondId = rs.getInt("id");
        ps.setInt(1, firstId);
        ps.setInt(2, secondId);
        ps.executeUpdate();
    }

    public boolean isPending(String first, String second) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from friend_requests where from_user_id = ? and " +
                    "to_user_id = ?");
            ps.setString(1, first);
            ps.setString(2, second);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            return rs.next();
        } catch (SQLException throwables) {
            return false;
        }
    }
}
