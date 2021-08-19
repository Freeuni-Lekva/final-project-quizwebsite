package DAO;

import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleChoiceUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;
    private final AnswerDao h;

    public MultipleChoiceUnorderedResponseQuestionDao(Connection conn) {
        this.conn = conn;
        h = new AnswerDao(conn);
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
        List<MultipleChoiceUnorderedResponseQuestion> result1 = getQuestionsWithChoices(quizId);
        List<Question> result= new ArrayList<Question>();
        result.addAll(result1);
        return result;
    }

    public List<MultipleChoiceUnorderedResponseQuestion> getQuestionsWithChoices(long quizId) throws SQLException {
        List<MultipleChoiceUnorderedResponseQuestion> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions WHERE  quiz_id = ?;");
        st.setLong(1, quizId);
        ResultSet res = st.executeQuery();

        while (res.next()) {
            String text = res.getString("question_text");
            long question_id = res.getLong("id");
            String legalStm = "select * from multiple_choice_unordered_answers  WHERE question_id = ? AND is_correct;";
            String choicesStm = "select * from  multiple_choice_unordered_answers  WHERE question_id = ?;";
            HashSet<String> legalAnswers = h.getAnswers(question_id, legalStm);
            HashSet<String> choices = h.getAnswers(question_id, choicesStm);
            MultipleChoiceUnorderedResponseQuestion q = new MultipleChoiceUnorderedResponseQuestion(text, legalAnswers, choices);
            result.add(q);
        }
        return result;
    }

}
