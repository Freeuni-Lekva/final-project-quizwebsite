package DAO;

import database.DatabaseConnection;
import mailbox.Message;
import user.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    Connection connection;


    public MessageDao(Connection connection) throws SQLException {
        this.connection = connection;
    }
    public void addMessage(Message msg) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("INSERT INTO messages(from_user_id, to_user_id, msg_text, sent_time) VALUES(?,?,?,?)");
        statement.setLong(1,msg.getFromId());
        statement.setLong(2, msg.getToId());
        statement.setString(3, msg.getMessage());
        statement.setTimestamp(4, msg.getSentTime());
        statement.executeUpdate();
    }

    public List<Message> getConversation(long firstId, long secondId) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement(
                "select * from messages where ((from_user_id = ? and to_user_id = ?) or (from_user_id = ? and to_user_id = ?)) order by sent_time");
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        statement.setLong(3, secondId);
        statement.setLong(4, firstId);
        List<Message> answer = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String msg = rs.getString("msg_text");
            Timestamp time = rs.getTimestamp("sent_time");
            long messageId = rs.getLong("id");
            long to_user_id = rs.getLong("to_user_id");
            long from_user_id = rs.getLong("from_user_id");
            Message curMessage = new Message(messageId,to_user_id,from_user_id,msg,time);
            answer.add(curMessage);
        }
        return answer;
    }
}
