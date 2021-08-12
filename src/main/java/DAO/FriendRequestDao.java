package DAO;

import mailbox.FriendRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDao {
    Connection connection;

    public FriendRequestDao(Connection connection) throws SQLException {
        this.connection = connection;

    }
    public void addFriendRequest(FriendRequest fr) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("INSERT INTO friend_requests(from_user_id, to_user_id) VALUES (?, ?)");
        statement.setLong(1, fr.getFromId());
        statement.setLong(2,fr.getToId());
        statement.executeUpdate();
    }
    public void removeFriendRequest(FriendRequest fr) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("DELETE FROM friend_requests where from_user_id = ? and to_user_id = ?");
        statement.setLong(1, fr.getFromId());
        statement.setLong(2,fr.getToId());
        statement.executeUpdate();
    }

    public List<FriendRequest> getFriendRequests(long toId) throws SQLException {
        List<FriendRequest> answer = new ArrayList<>();
        PreparedStatement statement =  connection.prepareStatement("select * from friend_requests where to_user_id = ?");
        statement.setLong(1,toId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            int from = rs.getInt("from_user_id");
            int to = rs.getInt("to_user_id");
            int requestId = rs.getInt("id");
            FriendRequest cur = new FriendRequest(requestId, from, to);
            answer.add(cur);
        }
        return answer;
    }

}
