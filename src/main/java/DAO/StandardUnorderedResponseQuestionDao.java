package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import question.StandardUnorderedResponseQuestion;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StandardUnorderedResponseQuestionDao extends QuestionDaoAbstract{
    Connection conn;

    public StandardUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question) throws SQLException {
            StandardUnorderedResponseQuestion q = (StandardUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO standard_unordered_questions(question_text, quiz_id) VALUES (?, ?);",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getQuizId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);
            HashSet<String> answers = q.getLegalAnswers();
            String st = "INSERT  INTO standard_unordered_answers(answer_text, question_id) VALUES (?, ?);";
            insertAnswers(st, conn, question_id, answers);
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
                String s="select * from standard_unordered_answers  WHERE question_id = ?;";
                HashSet<String> legalAnswers = getAnswers(question_id, s, conn);
                StandardUnorderedResponseQuestion q = new StandardUnorderedResponseQuestion(text, legalAnswers);
                q.setQuizId(quizId);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
