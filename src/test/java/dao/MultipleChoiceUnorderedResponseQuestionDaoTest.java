package dao;

import DAO.MultipleChoiceUnorderedResponseQuestionDao;
import database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import question.StandardUnorderedResponseQuestion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class MultipleChoiceUnorderedResponseQuestionDaoTest {
    private static Connection conn;
    private static MultipleChoiceUnorderedResponseQuestion q1;
    private static MultipleChoiceUnorderedResponseQuestion q2;
    private static MultipleChoiceUnorderedResponseQuestionDao qDao;


    @Before
    public void init() throws ClassNotFoundException, SQLException, IOException {
        conn= DatabaseConnection.getConnection();
        DatabaseConnection.resetTables();


        String  questionText = "whch is the highest Mountain?";
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("Chomolungma");
        legalAnswers.add("Mount Everest");
        legalAnswers.add("Everest");

        HashSet<String> choices = new HashSet<>();
        choices.add("Chomolungma");
        choices.add("Mount Everest");
        choices.add("Everest");
        choices.add("blabla");


        String  questionText2 = "What is the biggest sea";
        HashSet<String> legalAnswers2 = new HashSet<>();
        legalAnswers2.add("philippine Sea");

        HashSet<String> choices2 = new HashSet<>();
        choices2.add("philippine Sea");
        choices2.add("bla");
        choices2.add("blabla");
        choices2.add("blablabla");

        q1 = new MultipleChoiceUnorderedResponseQuestion(questionText, legalAnswers, choices);
        q2 = new MultipleChoiceUnorderedResponseQuestion(questionText2, legalAnswers2, choices2);
        qDao = new MultipleChoiceUnorderedResponseQuestionDao(conn);

        PreparedStatement statement = conn.prepareStatement
                ("INSERT  INTO users(username, hashed_password, is_admin, first_name, last_name)" +
                        "VALUES ('bla1', 'bla', false, 'bla', 'bla');");
        PreparedStatement statement1 = conn.prepareStatement
                ("INSERT  INTO quizzes (author, quiz_name,  is_random_order)" +
                        "VALUES (1, 'quiz1', false);");
        statement.execute();
        statement1.execute();
    }

    @Test
    public void testAddQuestion() throws SQLException {
        qDao.addQuestion(q1,1);

        PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions;", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery();

        res.last();
        String text = res.getString("question_text");
        long question_id = res.getLong("id");
        assertEquals(q1.getQuestionText(), text);


        HashSet<String> answers = new HashSet<>();
        PreparedStatement st1 = conn.prepareStatement("select * from multiple_choice_unordered_answers where question_id=?;");
        st1.setLong(1, question_id);
        ResultSet r = st1.executeQuery();
        while(r.next()){
            answers.add(r.getString("answer_text"));
        }
        assertEquals(q1.getChoices(), answers);

        PreparedStatement stat = conn.prepareStatement
                ("delete from multiple_choice_unordered_questions where question_text =?;");
        stat.setString(1, q1.getQuestionText());
        stat.execute();
    }

    @Test
    public void testGetQuestion() throws SQLException {
        qDao.addQuestion(q1, 1);
        qDao.addQuestion(q2, 1);

        List<Question>  qs =  qDao.getQuestions(1);
        assertEquals(qs.get(0).getQuestionText(), q1.getQuestionText());
        assertEquals(qs.get(1).getQuestionText(), q2.getQuestionText());

        MultipleChoiceUnorderedResponseQuestion p = (MultipleChoiceUnorderedResponseQuestion)qs.get(0);
        HashSet<String> r = p.getLegalAnswers();
        assertEquals(r, q1.getLegalAnswers());

        MultipleChoiceUnorderedResponseQuestion p1 = (MultipleChoiceUnorderedResponseQuestion)qs.get(1);
        HashSet<String> r1 = p1.getLegalAnswers();
        assertEquals(r1, q2.getLegalAnswers());

        PreparedStatement stat = conn.prepareStatement
                ("delete from multiple_choice_unordered_questions where question_text In (?, ?);");
        stat.setString(1, q1.getQuestionText());
        stat.setString(2, q2.getQuestionText());
        stat.execute();
    }
}