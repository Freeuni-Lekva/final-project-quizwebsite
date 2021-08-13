package question;

import DAO.QuestionDao;
import org.junit.Before;
import org.junit.Test;
import response.MultipleOrderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestMultipleAnswerUnorderedResponseQuestion {
    private MultipleAnswerUnorderedResponseQuestion question;
    HashSet<String> legalAnswers;

    @Before
    public void init(){
        legalAnswers = new HashSet<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");
        question = new MultipleAnswerUnorderedResponseQuestion("test", legalAnswers, 2);
    }

    @Test
    public void testGetScore() {
        ArrayList<String> responseAnswers = new ArrayList<>();
        responseAnswers.add("ans1");
        Response response = new MultipleOrderedAnswerResponse(responseAnswers);
        assertEquals(0.5, question.getScore(response), 0.01);
    }
    @Test
    public void testGetNumOfRequestedAnswers() throws SQLException, ClassNotFoundException {
        assertEquals(2, question.getNumOfRequestedAnswers());
        QuestionDao dao = question.getDao();
    }

    @Test
    public void testGetQuestion() {
        assertEquals("test", question.getQuestionText());
        assertEquals(legalAnswers, question.getLegalAnswers());
    }
}
