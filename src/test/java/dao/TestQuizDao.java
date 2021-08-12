package dao;

import DAO.QuizDao;
import DAO.UserDao;
import database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import question.StandardUnorderedResponseQuestion;
import quiz.Quiz;
import quiz.QuizAttempt;
import quiz.RandomOrderQuiz;
import user.Hash;
import user.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestQuizDao {
    User testUser;
    User attemptUser;
    UserDao userDao;
    QuizDao quizDao;
    Quiz quiz;

    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException, NoSuchAlgorithmException {
        DatabaseConnection.resetTables();
        testUser = new User("username", new Hash("pass").hashPassword(), true, "firstname", "lastname");
        attemptUser = new User("trier", new Hash("passs").hashPassword(), true, "fsaf", "fda");
        userDao = new UserDao(DatabaseConnection.getConnection());
        quizDao = new QuizDao(DatabaseConnection.getConnection());

        userDao.addUser(testUser);

        String  questionText = "which is the highest Mountain?";
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("Chomolungma");
        legalAnswers.add("Mount Everest");
        legalAnswers.add("Everest");

        Question q1 = new StandardUnorderedResponseQuestion(questionText, legalAnswers);


        String  questionText2 = "What is the biggest sea";
        HashSet<String> legalAnswers2 = new HashSet<>();
        legalAnswers2.add("philippine Sea");

        HashSet<String> choices2 = new HashSet<>();
        choices2.add("philippine Sea");
        choices2.add("bla");
        choices2.add("blabla");
        choices2.add("blablabla");

        Question q2 = new MultipleChoiceUnorderedResponseQuestion(questionText2, legalAnswers2, choices2);

        List<Question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);

        quiz = new RandomOrderQuiz(questionList, testUser, "quiz_name", new ArrayList<>());
        quizDao.addQuiz(quiz);
    }

    @Test
    public void testAdd() throws SQLException, ClassNotFoundException {
        Quiz newQuiz = quizDao.getQuiz(quiz.getId());
        assertEquals(quiz.getId(), newQuiz.getId());
        assertEquals("quiz_name", newQuiz.getName());
        assertEquals("username", newQuiz.getAuthor().getUsername());
        assertEquals(2, newQuiz.getQuestions().size());
    }

    @Test
    public void testHistory() throws SQLException, ClassNotFoundException {
        userDao.addUser(attemptUser);

        QuizAttempt attempt = new QuizAttempt(attemptUser.getId(), 37.6, new Timestamp(System.currentTimeMillis()));
        quizDao.addAttempt(quiz.getId(), attempt);

        Quiz newQuiz = quizDao.getQuiz(quiz.getId());
        assertEquals(1, newQuiz.getHistory().size());
        assertEquals(1, quizDao.getQuizzes().size());
    }

    @Test
    public void testRemove() throws SQLException, ClassNotFoundException {
        quizDao.removeQuiz(quiz.getId());
        assertEquals(0, quizDao.getQuizzes().size());
    }
}
