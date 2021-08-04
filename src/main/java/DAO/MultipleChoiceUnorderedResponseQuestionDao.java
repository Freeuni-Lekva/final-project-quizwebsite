package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleChoiceUnorderedResponseQuestionDao implements QuestionDao{
    Connection conn;

    public MultipleChoiceUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question) {
        MultipleChoiceUnorderedResponseQuestion q = (MultipleChoiceUnorderedResponseQuestion)question;
        try {
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_choice_unordered_questions (question_text, quiz_id)" +
                            "VALUES (?, 1);");
            statement.setString(1, q.getQuestionText());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestions(Quiz quiz) {
        List<Question> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, (int)quiz.getId());
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                int question_id = res.getInt("id");
                HashSet<String> legalAnswers = getLegalAnswers(question_id);
                HashSet<String> choices = getChoices(question_id);
                Question q = new MultipleChoiceUnorderedResponseQuestion(text, legalAnswers, choices);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private HashSet<String> getChoices(int question_id) {
        HashSet<String> result = new HashSet<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from  multiple_choice_unordered_answers  WHERE question_id = ?;" );
            st.setInt(1, question_id);
            ResultSet res = st.executeQuery();
            while(res.next()){
                result.add(res.getString("answer_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private HashSet<String> getLegalAnswers(int question_id) {
        HashSet<String> result = new HashSet<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_answers WHERE question_id = ? AND is_correct;" );
            st.setInt(1, question_id);
            ResultSet res = st.executeQuery();
            while(res.next()){
                result.add(res.getString("answer_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
