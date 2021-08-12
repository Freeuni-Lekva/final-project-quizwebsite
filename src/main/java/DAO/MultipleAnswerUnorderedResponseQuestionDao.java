package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleAnswerUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;

    public MultipleAnswerUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        MultipleAnswerUnorderedResponseQuestion q = (MultipleAnswerUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_answer_unordered_questions (question_text, quiz_id, numOfRequestedAnswers)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setLong(2, quiz_id);
            statement.setInt(3, q.getNumOfRequestedAnswers());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();

            int question_id =rs.getInt(1);
            String st = "Insert into  multiple_answer_unordered_answers(answer_text, question_id) values (?, ?);";
            HashSet<String> answers = q.getLegalAnswers();
            insertAnswers(st, conn, question_id, answers);
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions WHERE  quiz_id = ?;" );
        st.setLong(1, quizId);
        ResultSet res = st.executeQuery();
        while(res.next()){
            String text = res.getString("question_text");
            int question_id = res.getInt("id");
            String s = "select * from multiple_answer_unordered_answers WHERE question_id = ?;";
            HashSet<String> legalAnswers = getAnswers(question_id,  s, conn);
            int numOfRequestedAnswers = res.getInt("numOfRequestedAnswers");
            MultipleAnswerUnorderedResponseQuestion q = new MultipleAnswerUnorderedResponseQuestion(text, legalAnswers, numOfRequestedAnswers);
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
