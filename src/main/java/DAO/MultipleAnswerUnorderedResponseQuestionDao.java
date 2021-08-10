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
    public void addQuestion(Question question) throws SQLException {
        MultipleAnswerUnorderedResponseQuestion q = (MultipleAnswerUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_answer_unordered_questions (question_text, quiz_id, numOfRequestedAnswers)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getQuizId());
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
    public List<Question> getQuestions(int quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions WHERE  quiz_id = ?;" );
        st.setInt(1, quizId);
        ResultSet res = st.executeQuery();
        while(res.next()){
            String text = res.getString("question_text");
            int question_id = res.getInt("id");
            String s = "select * from multiple_answer_unordered_answers WHERE question_id = ?;";
            HashSet<String> legalAnswers = getAnswers(question_id,  s, conn);
            int numOfRequestedAnswers = res.getInt("numOfRequestedAnswers");
            MultipleAnswerUnorderedResponseQuestion q = new MultipleAnswerUnorderedResponseQuestion(text, legalAnswers, numOfRequestedAnswers);
            q.setQuizId(quizId);
            result.add(q);
        }

        return result;
    }
    private void insertAnswers(String st, Connection conn, int question_id, HashSet<String> answers) throws SQLException {
        for(String s : answers){
            PreparedStatement statement1 = conn.prepareStatement(st);
            statement1.setString(1, s);
            statement1.setInt(2, question_id);
            statement1.execute();
        }
    }


    private HashSet<String> getAnswers(int question_id, String s, Connection conn) throws SQLException {
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
