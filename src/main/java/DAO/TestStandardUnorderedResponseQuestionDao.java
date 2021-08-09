package DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import question.Question;
import question.StandardUnorderedResponseQuestion;
import quiz.Quiz;
import quiz.StandardQuiz;

import static org.junit.jupiter.api.Assertions.*;

import javax.enterprise.inject.Stereotype;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestStandardUnorderedResponseQuestionDao {
    private static Connection conn;
    private static StandardUnorderedResponseQuestionDao qDao;
    private static StandardUnorderedResponseQuestion q1;
    private static StandardUnorderedResponseQuestion q2 ;

    @BeforeAll
    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizwebsite_db", "root1", "Rootroot!123");

        qDao = new StandardUnorderedResponseQuestionDao(conn);

        String  questionText = "which is the highest Mountain?";
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("Chomolungma");
        legalAnswers.add("Mount Everest");
        legalAnswers.add("Everest");

        q1 = new StandardUnorderedResponseQuestion(questionText, legalAnswers);

        String  questionText2 = "What is the biggest sea";
        HashSet<String> legalAnswers2 = new HashSet<>();
        legalAnswers2.add("philippine Sea");

        q2 = new StandardUnorderedResponseQuestion(questionText2, legalAnswers2);
        q1.setQuizId(1);
        q2.setQuizId(1);

    }

    @Test
    public void testAddQuestion() throws SQLException {
        qDao.addQuestion(q1);
        PreparedStatement st = conn.prepareStatement("select * from standard_unordered_questions;",ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery();

        res.last();
        String text = res.getString("question_text");
        int question_id = res.getInt("id");
        assertEquals(q1.getQuestionText(), text );


        HashSet<String> answers = new HashSet<>();
        PreparedStatement st1 = conn.prepareStatement("select * from standard_unordered_answers where question_id=?;");
        st1.setInt(1, question_id);
        ResultSet r = st1.executeQuery();
        while(r.next()){
            answers.add(r.getString("answer_text"));
        }
        assertEquals(q1.getLegalAnswers(), answers);
        PreparedStatement stat = conn.prepareStatement("delete from standard_unordered_questions where question_text =?;");
        stat.setString(1, q1.getQuestionText());
        stat.execute();
    }

    @Test
    public void testGetQuestion() throws SQLException {
        qDao.addQuestion(q1);
        qDao.addQuestion(q2);

        List<Question>  qs =  qDao.getQuestions(1);
        assertEquals(qs.get(0).getQuestionText(), q1.getQuestionText());
        assertEquals(qs.get(1).getQuestionText(), q2.getQuestionText());

        StandardUnorderedResponseQuestion p = (StandardUnorderedResponseQuestion)qs.get(0);
        HashSet<String> r = p.getLegalAnswers();
        assertEquals(r, q1.getLegalAnswers());

        StandardUnorderedResponseQuestion p1 = (StandardUnorderedResponseQuestion)qs.get(1);
        HashSet<String> r1 = p1.getLegalAnswers();
        assertEquals(r1, q2.getLegalAnswers());

        PreparedStatement stat = conn.prepareStatement("delete from standard_unordered_questions where question_text in (?, ?);");
        stat.setString(1, q1.getQuestionText());
        stat.setString(2, q2.getQuestionText());
        stat.execute();
    }
}