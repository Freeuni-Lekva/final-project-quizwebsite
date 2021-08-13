package question;

import DAO.QuestionDao;
import org.junit.Before;
import org.junit.Test;
import response.MultipleOrderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;


import static org.junit.Assert.assertEquals;

public class TestMultipleChoiceUnorderedResponseQuestion {
    private MultipleChoiceUnorderedResponseQuestion question;
    private HashSet<String> choices;
    HashSet<String> legalAnswers;


    @Before
    public void init(){
        legalAnswers = new HashSet<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");
        legalAnswers.add("ans4");

        choices = new HashSet<>();
        choices.add("ans1");
        choices.add("ans2");
        choices.add("ans3");
        choices.add("ans4");
        choices.add("bla");
        choices.add("blabla");

        question = new MultipleChoiceUnorderedResponseQuestion("test", legalAnswers, choices);
    }

    @Test
    public void testGetScore() {
        ArrayList<String> responseAnswers = new ArrayList<>();
        responseAnswers.add("ans1");
        responseAnswers.add("ans2");
        Response response = new MultipleOrderedAnswerResponse(responseAnswers);
        assertEquals(0.5, question.getScore(response), 0.01);
    }
    @Test
    public void testGetChoices() throws SQLException, ClassNotFoundException {
        assertEquals(question.getChoices(), choices);
        QuestionDao dao = question.getDao();
    }
}
