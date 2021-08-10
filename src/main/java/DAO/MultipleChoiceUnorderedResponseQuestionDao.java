package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleChoiceUnorderedResponseQuestionDao extends QuestionDaoAbstract {
    Connection conn;

    public MultipleChoiceUnorderedResponseQuestionDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addQuestion(Question question) throws SQLException {
        MultipleChoiceUnorderedResponseQuestion q = (MultipleChoiceUnorderedResponseQuestion) question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_choice_unordered_questions (question_text, quiz_id)" +
                            "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getQuizId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id = rs.getInt(1);

            HashSet<String> legalAnswers = q.getLegalAnswers();
            HashSet<String> choices = q.getChoices();
            for (String s : choices) {
                PreparedStatement statement1 = conn.prepareStatement
                        ("Insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values (?, ?, ?);");
                statement1.setString(1, s);
                statement1.setInt(2, question_id);
                statement1.setBoolean(3, legalAnswers.contains(s));
                statement1.execute();
            }
    }

    @Override
    public List<Question> getQuestions(int quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions WHERE  quiz_id = ?;");
            st.setInt(1, quizId);
            ResultSet res = st.executeQuery();

            while (res.next()) {
                String text = res.getString("question_text");
                int question_id = res.getInt("id");
                String legalStm = "select * from multiple_choice_unordered_answers  WHERE question_id = ? AND is_correct;";
                String choicesStm = "select * from  multiple_choice_unordered_answers  WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, legalStm, conn);
                HashSet<String> choices = getAnswers(question_id, choicesStm, conn);
                MultipleChoiceUnorderedResponseQuestion q = new MultipleChoiceUnorderedResponseQuestion(text, legalAnswers, choices);
                q.setQuizId(quizId);
                result.add(q);
            }
        return result;
    }
}
