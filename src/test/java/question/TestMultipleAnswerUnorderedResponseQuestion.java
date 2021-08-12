package question;

import org.junit.Test;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import response.MultipleOrderedAnswerResponse;
import response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestMultipleAnswerUnorderedResponseQuestion {
    @Test
    public void testGetScore() {
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");
        Question question = new MultipleAnswerUnorderedResponseQuestion("test", legalAnswers, 2);
        ArrayList<String> responseAnswers = new ArrayList<>();
        responseAnswers.add("ans1");
        Response response = new MultipleOrderedAnswerResponse(responseAnswers);
        assertEquals(0.5, question.getScore(response), 0.01);
    }
}
