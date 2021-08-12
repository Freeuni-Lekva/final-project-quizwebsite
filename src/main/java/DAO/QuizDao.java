package DAO;

import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuizDao {
    private Connection connection;

    public QuizDao(Connection connection) {
        this.connection = connection;
    }

    void addQuiz(Quiz quiz) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("insert into quizzes (author, quiz_name) values (?, ?)");
        statement.setLong(1, quiz.getAuthor().getId());
        statement.setString(2, quiz.getName());
        List<Question> questions = quiz.getQuestions();
        for (Question question : questions) {
            question.getDao().addQuestion(question);
        }
    }

    void addAttempt(long quizId) {

    }

    void removeQuiz(long quizId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from quizzes where quiz_id = ?");
        statement.setLong(1, quizId);
        statement.executeUpdate();
    }
}
