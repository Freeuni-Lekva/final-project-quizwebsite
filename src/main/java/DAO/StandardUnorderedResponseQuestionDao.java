package DAO;

import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import question.StandardUnorderedResponseQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StandardUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;
    private final AnswerDao h;

    public StandardUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
        h= new AnswerDao(conn);
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
            StandardUnorderedResponseQuestion q = (StandardUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO standard_unordered_questions(question_text, quiz_id) VALUES (?, ?);",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setLong(2, quiz_id);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            long question_id =rs.getLong(1);
            HashSet<String> answers = q.getLegalAnswers();
            String st = "INSERT  INTO standard_unordered_answers(answer_text, question_id) VALUES (?, ?);";
            h.insertAnswers(st, question_id, answers);
    }

    @Override
    public List<Question> getQuestions(long quizId) {
        List<Question> result = new ArrayList<>();
        List<StandardUnorderedResponseQuestion> result1 = getQuestionsStandardUnordered(quizId);
        result.addAll(result1);
        return result;
    }

    public List<StandardUnorderedResponseQuestion> getQuestionsStandardUnordered(long quizId) {
        List<StandardUnorderedResponseQuestion> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from standard_unordered_questions WHERE  quiz_id = ?;" );
            st.setLong(1, quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                long question_id = res.getLong("id");
                String s="select * from standard_unordered_answers  WHERE question_id = ?;";
                HashSet<String> legalAnswers = h.getAnswers(question_id, s);
                StandardUnorderedResponseQuestion q = new StandardUnorderedResponseQuestion(text, legalAnswers);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
