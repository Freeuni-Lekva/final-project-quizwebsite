package DAO;

import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleChoiceUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;

    public MultipleChoiceUnorderedResponseQuestionDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        MultipleChoiceUnorderedResponseQuestion q = (MultipleChoiceUnorderedResponseQuestion) question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_choice_unordered_questions (question_text, quiz_id)" +
                            "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setLong(2,  quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            long question_id = rs.getLong(1);

            HashSet<String> legalAnswers = q.getLegalAnswers();
            HashSet<String> choices = q.getChoices();
            for (String s : choices) {
                PreparedStatement statement1 = conn.prepareStatement
                        ("Insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values (?, ?, ?);");
                statement1.setString(1, s);
                statement1.setLong(2, question_id);
                statement1.setBoolean(3, legalAnswers.contains(s));
                statement1.execute();
            }
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions WHERE  quiz_id = ?;");
            st.setLong(1, quizId);
            ResultSet res = st.executeQuery();

            while (res.next()) {
                String text = res.getString("question_text");
                long question_id = res.getLong("id");
                String legalStm = "select * from multiple_choice_unordered_answers  WHERE question_id = ? AND is_correct;";
                String choicesStm = "select * from  multiple_choice_unordered_answers  WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, legalStm, conn);
                HashSet<String> choices = getAnswers(question_id, choicesStm, conn);
                MultipleChoiceUnorderedResponseQuestion q = new MultipleChoiceUnorderedResponseQuestion(text, legalAnswers, choices);
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
