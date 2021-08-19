package DAO;

import question.MultipleChoiceUnorderedResponseQuestion;
import question.PictureUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PictureUnorderedResponseQuestionDao implements QuestionDao {

    private final Connection conn;
    private final AnswerDao h;


    public PictureUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
        this.h = new AnswerDao(conn);

    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
            PictureUnorderedResponseQuestion q = (PictureUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO picture_unordered_questions(question_text, img_url, quiz_id)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setString(2, q.getPicUrl());
            statement.setLong(3, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            long question_id =rs.getLong(1);

            HashSet<String> answers = q.getLegalAnswers();
            String st ="Insert into picture_unordered_answers(answer_text, question_id) values (?, ?);";
            h.insertAnswers(st, question_id, answers);
    }


    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<PictureUnorderedResponseQuestion> result1 = getQuestionsPictureUnordered(quizId);
        result.addAll(result1);
        return result;
    }

    public List<PictureUnorderedResponseQuestion> getQuestionsPictureUnordered(long quizId) throws SQLException {
        List<PictureUnorderedResponseQuestion> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("select * from picture_unordered_questions WHERE  quiz_id = ?;" );
        st.setLong(1, quizId);
        ResultSet res = st.executeQuery();

        while(res.next()){
            String text = res.getString("question_text");
            long question_id = res.getLong("id");
            String s = "select * from picture_unordered_answers WHERE question_id = ?;";
            HashSet<String> legalAnswers = h.getAnswers(question_id, s);
            String img_url = res.getString("img_url");
            PictureUnorderedResponseQuestion q = new PictureUnorderedResponseQuestion(text, legalAnswers, img_url);
            result.add(q);
        }
        return result;
    }


}
