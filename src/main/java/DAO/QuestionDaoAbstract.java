package DAO;

import question.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public abstract class QuestionDaoAbstract  implements  QuestionDao{


    protected void insertAnswers(String st, Connection conn, int question_id, HashSet<String> answers) throws SQLException {
        for(String s : answers){
            PreparedStatement statement1 = conn.prepareStatement(st);
            statement1.setString(1, s);
            statement1.setInt(2, question_id);
            statement1.execute();
        }
    }


    protected HashSet<String> getAnswers(int question_id, String s, Connection conn) throws SQLException {
        HashSet<String> result = new HashSet<>();
        PreparedStatement st = conn.prepareStatement(s);
        st.setInt(1, question_id);
        ResultSet res = st.executeQuery();
        while(res.next()){
            result.add(res.getString("answer_text"));
        }
        return result;
    }
}
