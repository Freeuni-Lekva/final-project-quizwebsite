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

    public boolean areFriends(int firstId, int secondId) throws SQLException {
        PreparedStatement ps2 = conn.prepareStatement("select * from friendship where (first_user_id = ? and" +
                "second_user_id = ?");
        ResultSet rs3, rs4;
        ps2.setInt(1, firstId);
        ps2.setInt(2, secondId);
        rs3 = ps2.executeQuery();
        ps2.setInt(1, secondId);
        ps2.setInt(2, firstId);
        rs4 = ps2.executeQuery();
        return rs3.next() || rs4.next();
    }

    public void addRequest(FriendRequest fr) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into friend_requests values (?, ?, ?)");
        ps.setInt(1, fr.getRequestId());
        ps.setInt(2, fr.getFromId());
        ps.setInt(3, fr.getToId());
        ps.executeUpdate();
    }

    public void removeRequest(FriendRequest fr) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from friend_requests where id = ?");
        ps.setInt(1, fr.getRequestId());
        ps.executeUpdate();
    }

    public boolean isPending(FriendRequest fr) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from friend_requests where id = ?");
        ps.setInt(1, fr.getRequestId());
        ResultSet rs;
        rs = ps.executeQuery();
        return rs.next();
    }
}
