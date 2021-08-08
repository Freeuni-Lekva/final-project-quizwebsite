package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.PictureUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PictureUnorderedResponseQuestionDao implements QuestionDao{

    Connection conn;

    public PictureUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question, int quiz_id) {
        PictureUnorderedResponseQuestion q = (PictureUnorderedResponseQuestion)question;
        try {

            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO picture_unordered_questions(question_text, img_url, quiz_id)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setString(2, q.getPicUrl());
            statement.setInt(3, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);

            HashSet<String> answers = q.getLegalAnswers();
            for(String s : answers){
                PreparedStatement statement1 = conn.prepareStatement
                        ("Insert into picture_unordered_answers(answer_text, question_id) values (?, ?);");
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
            PreparedStatement st = conn.prepareStatement("select * from picture_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, (int)quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                int question_id = res.getInt("id");
                HashSet<String> legalAnswers = getLegalAnswers(question_id);
                String img_url = res.getString("img_url");
                int quiz_id=res.getInt("quiz_id");
                Question q = new PictureUnorderedResponseQuestion(text, legalAnswers, img_url);
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
            PreparedStatement st = conn.prepareStatement("select * from picture_unordered_answers WHERE question_id = ?;" );
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
