package DAO;

import com.mysql.cj.protocol.Resultset;
import question.MultipleAnswerOrderedQuestion;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleAnswerUnorderedResponseQuestionDao implements QuestionDao{
    Connection conn;

    public MultipleAnswerUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question) {
        MultipleAnswerUnorderedResponseQuestion q = (MultipleAnswerUnorderedResponseQuestion)question;
        try {
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_answer_unordered_questions (question_text, quiz_id, numOfRequestedAnswers)" +
                            "VALUES (?, 1, ?);");
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getNumOfRequestedAnswers());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestions(Quiz quiz) {
        List<Question> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, (int)quiz.getId());
            ResultSet res = st.executeQuery();

            while(res.next()){
                    String text = res.getString("question_text");
                    int question_id = res.getInt("id");
                    HashSet<String> legalAnswers = getLegalAnswers(question_id);
                    int numOfRequestedAnswers = res.getInt("numOfRequestedAnswers");
                    Question q = new MultipleAnswerUnorderedResponseQuestion(text, legalAnswers, numOfRequestedAnswers);
                    result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private HashSet<String> getLegalAnswers(int question_id) {
        HashSet<String> result = new HashSet<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_answers WHERE question_id = ?;" );
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
