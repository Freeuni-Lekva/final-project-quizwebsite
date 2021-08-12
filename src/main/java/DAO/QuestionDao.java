package DAO;

import question.Question;
import quiz.Quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public interface QuestionDao {
    void addQuestion(Question question, long quiz_id) throws SQLException;
    List<Question> getQuestions(long quizId) throws SQLException;
}
