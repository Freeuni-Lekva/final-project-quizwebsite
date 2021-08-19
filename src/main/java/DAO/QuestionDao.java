package DAO;

import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDao {
    void addQuestion(Question question, long quiz_id) throws SQLException;
    List<Question> getQuestions(long quizId) throws SQLException;
}
