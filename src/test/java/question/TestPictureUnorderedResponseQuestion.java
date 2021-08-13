package question;

import DAO.QuestionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import response.MultipleOrderedAnswerResponse;
import response.MultipleUnorderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestPictureUnorderedResponseQuestion {
    private static PictureUnorderedResponseQuestion question;
    private static String picUrl = "bla";

    @BeforeAll
    public static void init(){
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");

        question = new PictureUnorderedResponseQuestion("test", legalAnswers, picUrl);
    }

    @Test
    public void testGetScore() {
        HashSet<String> responseAnswers = new HashSet<>();
        responseAnswers.add("ans1");
        Response response = new MultipleUnorderedAnswerResponse(responseAnswers);
        Assertions.assertEquals(1, question.getScore(response));
    }
    @Test
    public void testGetPicUrl() throws SQLException, ClassNotFoundException {
        QuestionDao dao = question.getDao();
        assertEquals(question.getPicUrl(), picUrl);
    }
}
