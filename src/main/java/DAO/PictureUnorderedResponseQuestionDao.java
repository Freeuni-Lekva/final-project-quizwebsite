package DAO;

import question.PictureUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PictureUnorderedResponseQuestionDao implements QuestionDao {

    private final Connection conn;

    public PictureUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
            PictureUnorderedResponseQuestion q = (PictureUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO picture_unordered_questions(question_text, img_url, quiz_id)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setString(2, q.getPicUrl());
            statement.setLong(3, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            long question_id =rs.getLong(1);

            HashSet<String> answers = q.getLegalAnswers();
            String st ="Insert into picture_unordered_answers(answer_text, question_id) values (?, ?);";
            insertAnswers(st, conn, question_id, answers);
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("select * from picture_unordered_questions WHERE  quiz_id = ?;" );
            st.setLong(1, quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                long question_id = res.getLong("id");
                String s = "select * from picture_unordered_answers WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, s, conn);
                String img_url = res.getString("img_url");
                PictureUnorderedResponseQuestion q = new PictureUnorderedResponseQuestion(text, legalAnswers, img_url);
                result.add(q);
            }
        return result;
    }

    private void insertAnswers(String st, Connection conn, long question_id, HashSet<String> answers) throws SQLException {
        for(String s : answers){
            PreparedStatement statement1 = conn.prepareStatement(st);
            statement1.setString(1, s);
            statement1.setLong(2, question_id);
            statement1.execute();
        }
    }


    private HashSet<String> getAnswers(long question_id, String s, Connection conn) throws SQLException {
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