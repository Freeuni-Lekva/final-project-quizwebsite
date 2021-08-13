package question;

import DAO.QuestionDao;
import org.junit.Before;
import org.junit.Test;
import response.MultipleUnorderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestStandardUnorderedResponseQuestion {
    private static StandardUnorderedResponseQuestion question;
    private static String picUrl = "bla";
    private HashSet<String> legalAnswers;

    @Before
    public void init(){
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");

        question = new StandardUnorderedResponseQuestion("test", legalAnswers);
    }

    @Test
    public void testGetScore() throws SQLException, ClassNotFoundException {
        HashSet<String> responseAnswers = new HashSet<>();
        responseAnswers.add("ans1");
        Response response = new MultipleUnorderedAnswerResponse(responseAnswers);
        assertEquals(1, question.getScore(response), 0.01);
        QuestionDao dao = question.getDao();
    }
}
