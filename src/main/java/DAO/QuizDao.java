package DAO;

import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import quiz.QuizAttempt;
import quiz.RandomOrderQuiz;
import quiz.StandardQuiz;
import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDao {
    private Connection connection;

    public QuizDao(Connection connection) {
        this.connection = connection;
    }

    public void addQuiz(Quiz quiz) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("insert into quizzes (author, quiz_name, is_random_order) values (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setLong(1, quiz.getAuthor().getId());
        statement.setString(2, quiz.getName());
        statement.setBoolean(3, quiz instanceof RandomOrderQuiz);
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        quiz.setId(id);

        List<Question> questions = quiz.getQuestions();
        for (Question question : questions) {
            question.getDao().addQuestion(question, quiz.getId());
        }
    }

    public Quiz getQuiz(long quizId) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao(DatabaseConnection.getConnection());

        PreparedStatement statement = connection.prepareStatement("select * from quizzes where id = ?");
        statement.setLong(1, quizId);
        ResultSet rs = statement.executeQuery();
        rs.next();
        String quizName = rs.getString("quiz_name");
        long authorId = rs.getLong("author");
        boolean isRandomOrder = rs.getBoolean("is_random_order");
        User author = dao.getUser(authorId);

        List<Question> questions = getQuestions(quizId);
        List<QuizAttempt> history = getHistory(quizId);

        return (isRandomOrder) ? new RandomOrderQuiz(quizId, questions, author, quizName, history) : new StandardQuiz(quizId, questions, author, quizName, history);
    }

    public void addAttempt(long quizId, QuizAttempt quizAttempt) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into quiz_history (quiz_id, user_id, score, attempt_time) values (?, ?, ?, ?)");
        statement.setLong(1, quizId);
        statement.setLong(2, quizAttempt.getUserId());
        statement.setDouble(3, quizAttempt.getScore());
        statement.setTimestamp(4, quizAttempt.getTimestamp());
        statement.executeUpdate();
    }

    public void removeQuiz(long quizId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from quizzes where id = ?");
        statement.setLong(1, quizId);
        statement.executeUpdate();
    }

    public List<Quiz> getQuizzes() throws SQLException, ClassNotFoundException {
        List<Quiz> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from quizzes order by id desc");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            result.add(getQuiz(rs.getLong("id")));
        }
        return result;
    }

    private List<Question> getQuestions(long quizId) throws SQLException, ClassNotFoundException {
        List<Question> result = new ArrayList<>();
        QuestionDao dao1 = new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        QuestionDao dao2 = new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        QuestionDao dao3 = new MultipleChoiceUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
        QuestionDao dao4 = new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());

        result.addAll(dao1.getQuestions(quizId));
        result.addAll(dao2.getQuestions(quizId));
        result.addAll(dao3.getQuestions(quizId));
        result.addAll(dao4.getQuestions(quizId));

        return result;
    }

    private List<QuizAttempt> getHistory(long quizId) throws SQLException {
        List<QuizAttempt> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from quiz_history where quiz_id = ?");
        statement.setLong(1, quizId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            result.add(new QuizAttempt(rs.getLong("user_id"), rs.getDouble("score"), rs.getTimestamp("attempt_time")));
        }
        return result;
    }
}
