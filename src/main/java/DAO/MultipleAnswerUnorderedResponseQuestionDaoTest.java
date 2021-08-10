package DAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;

import java.sql.*;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleAnswerUnorderedResponseQuestionDaoTest {
    private static Connection conn;
    private static MultipleAnswerUnorderedResponseQuestion q1;
    private static MultipleAnswerUnorderedResponseQuestion q2;
    private static MultipleAnswerUnorderedResponseQuestionDao qDao;



    @BeforeAll
    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizwebsite_db", "root1", "Rootroot!123");


        String  questionText = "which is the highest Mountain?";
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("Chomolungma");
        legalAnswers.add("Mount Everest");
        legalAnswers.add("Everest");
        int numOfRequestedAnswers = 2;


        String  questionText2 = "What is the biggest sea";
        HashSet<String> legalAnswers2 = new HashSet<>();
        legalAnswers2.add("philippine Sea");
        int numOfRequestedAnswers2 = 1;

        q1 = new MultipleAnswerUnorderedResponseQuestion(questionText, legalAnswers, numOfRequestedAnswers);
        q2 = new MultipleAnswerUnorderedResponseQuestion(questionText2, legalAnswers2, numOfRequestedAnswers2);
        q1.setQuizId(1);
        q2.setQuizId(1);
        qDao = new MultipleAnswerUnorderedResponseQuestionDao(conn);
    }

    @Test
    public void testAddQuestion() throws SQLException {
        qDao.addQuestion(q1);

        PreparedStatement st = conn.prepareStatement("select * from multiple_answer_unordered_questions;", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery();

        res.last();
        String text = res.getString("question_text");
        int question_id = res.getInt("id");
        assertEquals(q1.getQuestionText(), text);


        HashSet<String> answers = new HashSet<>();
        PreparedStatement st1 = conn.prepareStatement("select * from multiple_answer_unordered_answers where question_id=?;");
        st1.setInt(1, question_id);
        ResultSet r = st1.executeQuery();
        while(r.next()){
            answers.add(r.getString("answer_text"));
        }
        assertEquals(q1.getLegalAnswers(), answers);
        assertEquals(q1.getNumOfRequestedAnswers(), res.getInt("numOfRequestedAnswers"));

        PreparedStatement stat = conn.prepareStatement("delete from multiple_answer_unordered_questions where question_text=?;");
        stat.setString(1, q1.getQuestionText());
        stat.execute();
    }

    @Test
    public void testGetQuestion() throws SQLException {
        qDao.addQuestion(q1);
        qDao.addQuestion(q2);

        List<Question> qs =  qDao.getQuestions(1);
        assertEquals(qs.get(0).getQuestionText(), q1.getQuestionText());
        assertEquals(qs.get(1).getQuestionText(), q2.getQuestionText() );

        MultipleAnswerUnorderedResponseQuestion p = (MultipleAnswerUnorderedResponseQuestion)qs.get(0);
        HashSet<String> r = p.getLegalAnswers();
        assertEquals(r, q1.getLegalAnswers());
        assertEquals(p.getNumOfRequestedAnswers(), q1.getNumOfRequestedAnswers());

        MultipleAnswerUnorderedResponseQuestion p1 = (MultipleAnswerUnorderedResponseQuestion)qs.get(1);
        HashSet<String> r1 = p1.getLegalAnswers();
        assertEquals(r1, q2.getLegalAnswers());
        assertEquals(p1.getNumOfRequestedAnswers(), q2.getNumOfRequestedAnswers());


        PreparedStatement stat = conn.prepareStatement("delete from multiple_answer_unordered_questions where question_text In (?, ?);");
        stat.setString(1, q1.getQuestionText());
        stat.setString(2, q2.getQuestionText());
        stat.execute();
    }
}