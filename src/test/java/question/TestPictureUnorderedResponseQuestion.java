package question;

import DAO.QuestionDao;
import org.junit.Before;
import org.junit.Test;
import response.MultipleUnorderedAnswerResponse;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestPictureUnorderedResponseQuestion {
    private PictureUnorderedResponseQuestion question;
    private String picUrl = "bla";

    @Before
    public void init(){
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
        assertEquals(1, question.getScore(response), 0.01);
    }
    @Test
    public void testGetPicUrl() throws SQLException, ClassNotFoundException {
        QuestionDao dao = question.getDao();
        assertEquals(question.getPicUrl(), picUrl);
    }
}
