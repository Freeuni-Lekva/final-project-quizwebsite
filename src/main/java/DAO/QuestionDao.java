package DAO;

import question.Question;
import quiz.Quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public interface QuestionDao {
    void addQuestion(Question question) throws SQLException;
    List<Question> getQuestions(int quizId) throws SQLException;
}
