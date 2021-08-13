package question;

import DAO.QuestionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import response.MultipleUnorderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestStandardUnorderedResponseQuestion {
    private static StandardUnorderedResponseQuestion question;
    private static String picUrl = "bla";

    @BeforeAll
    public static void init(){
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
        Assertions.assertEquals(1, question.getScore(response));
        QuestionDao dao = question.getDao();
    }
}
