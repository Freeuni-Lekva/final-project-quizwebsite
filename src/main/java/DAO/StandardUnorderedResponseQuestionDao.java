package DAO;

import question.Question;
import question.StandardUnorderedResponseQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StandardUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;

    public StandardUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
            StandardUnorderedResponseQuestion q = (StandardUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO standard_unordered_questions(question_text, quiz_id) VALUES (?, ?);",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setLong(2, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);
            HashSet<String> answers = q.getLegalAnswers();
            String st = "INSERT  INTO standard_unordered_answers(answer_text, question_id) VALUES (?, ?);";
            insertAnswers(st, conn, question_id, answers);
    }

    @Override
    public List<Question> getQuestions(int quizId) {
        List<Question> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from standard_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                int question_id = res.getInt("id");
                String s="select * from standard_unordered_answers  WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, s, conn);
                StandardUnorderedResponseQuestion q = new StandardUnorderedResponseQuestion(text, legalAnswers);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
