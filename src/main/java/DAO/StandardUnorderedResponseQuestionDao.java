package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import question.StandardUnorderedResponseQuestion;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StandardUnorderedResponseQuestionDao implements QuestionDao{
    Connection conn;

    public StandardUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question, int quiz_id) {
        StandardUnorderedResponseQuestion q = (StandardUnorderedResponseQuestion)question;
        try {
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO standard_unordered_questions(question_text, quiz_id) VALUES (?, ?);",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);

            HashSet<String> answers = q.getLegalAnswers();
            for(String s : answers){
                PreparedStatement st= conn.prepareStatement
                        ("INSERT  INTO standard_unordered_answers(answer_text, question_id) VALUES (?, ?);");
                st.setString(1, s);
                st.setInt(2, question_id);
                st.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                HashSet<String> legalAnswers = getLegalAnswers(question_id);
                int quiz_id = res.getInt("quiz_id");
                Question q = new StandardUnorderedResponseQuestion(text, legalAnswers);
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
            PreparedStatement st = conn.prepareStatement("select * from standard_unordered_answers  WHERE question_id = ?;" );
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
