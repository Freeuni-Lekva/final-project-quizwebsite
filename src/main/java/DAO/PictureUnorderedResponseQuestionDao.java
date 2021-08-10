package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.PictureUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PictureUnorderedResponseQuestionDao extends QuestionDaoAbstract{

    Connection conn;

    public PictureUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question) throws SQLException {
            PictureUnorderedResponseQuestion q = (PictureUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO picture_unordered_questions(question_text, img_url, quiz_id)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setString(2, q.getPicUrl());
            statement.setInt(3, q.getQuizId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);

            HashSet<String> answers = q.getLegalAnswers();
            String st ="Insert into picture_unordered_answers(answer_text, question_id) values (?, ?);";
            insertAnswers(st, conn, question_id, answers);
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
                String s = "select * from picture_unordered_answers WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, s, conn);
                String img_url = res.getString("img_url");
                PictureUnorderedResponseQuestion q = new PictureUnorderedResponseQuestion(text, legalAnswers, img_url);
                q.setQuizId(quizId);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



}
