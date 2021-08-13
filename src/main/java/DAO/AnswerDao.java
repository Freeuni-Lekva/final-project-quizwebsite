package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class AnswerDao {
    private final Connection conn;

    public AnswerDao(Connection conn){
        this.conn=conn;
    }

    public void insertAnswers(String st, long question_id, HashSet<String> answers) throws SQLException {
        for(String s : answers){
            PreparedStatement statement1 = conn.prepareStatement(st);
            statement1.setString(1, s);
            statement1.setLong(2, question_id);
            statement1.execute();
        }
    }


    public HashSet<String> getAnswers(long question_id, String s) throws SQLException {
        HashSet<String> result = new HashSet<>();
        PreparedStatement st = conn.prepareStatement(s);
        st.setLong(1, question_id);
        ResultSet res = st.executeQuery();
        while(res.next()){
            result.add(res.getString("answer_text"));
        }
        return result;
    }
}
