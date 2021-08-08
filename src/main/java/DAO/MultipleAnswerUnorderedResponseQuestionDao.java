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
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getQuizId());
            statement.setInt(3, q.getNumOfRequestedAnswers());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);

            HashSet<String> answers = q.getLegalAnswers();
            for(String s : answers){
                PreparedStatement statement1 = conn.prepareStatement
                        ("Insert into  multiple_answer_unordered_answers(answer_text, question_id) values (?, ?);");
                statement1.setString(1, s);
                statement1.setInt(2, question_id);
                statement1.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestions(int quizId) {
        List<Question> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                    String text = res.getString("question_text");
                    int question_id = res.getInt("id");
                    int quiz_id = res.getInt("quiz_id");
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
