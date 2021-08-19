package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class MultipleAnswerUnorderedResponseQuestionDao implements QuestionDao {
    private final Connection conn;
    private final AnswerDao h;

    public MultipleAnswerUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
        this.h = new AnswerDao(conn);
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        MultipleAnswerUnorderedResponseQuestion q = (MultipleAnswerUnorderedResponseQuestion)question;
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_answer_unordered_questions (question_text, quiz_id, numOfRequestedAnswers)" +
                            "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setLong(2, quiz_id);
            statement.setLong(3, q.getNumOfRequestedAnswers());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();

            long question_id = rs.getLong(1);
            String st = "Insert into  multiple_answer_unordered_answers(answer_text, question_id) values (?, ?);";
            HashSet<String> answers = q.getLegalAnswers();


            h.insertAnswers(st, question_id, answers);
    }

    @Override
    public List<Question> getQuestions(long  quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List <MultipleAnswerUnorderedResponseQuestion> result1 =getQuestionsMultipleAnsUnordered(quizId);
        result.addAll(result1);
        return result;
    }

    public List<MultipleAnswerUnorderedResponseQuestion> getQuestionsMultipleAnsUnordered(long  quizId) throws SQLException {
        List<MultipleAnswerUnorderedResponseQuestion> result = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions WHERE  quiz_id = ?;" );
        st.setLong(1, quizId);
        ResultSet res = st.executeQuery();
        while(res.next()){
            String text = res.getString("question_text");
            long question_id = res.getLong("id");
            String s = "select * from multiple_answer_unordered_answers WHERE question_id = ?;";
            HashSet<String> legalAnswers = h.getAnswers(question_id,  s);
            long numOfRequestedAnswers = res.getLong("numOfRequestedAnswers");
            MultipleAnswerUnorderedResponseQuestion q = new MultipleAnswerUnorderedResponseQuestion(text, legalAnswers, numOfRequestedAnswers);
            result.add(q);
        }

        return result;
    }


}
