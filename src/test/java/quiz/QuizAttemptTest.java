package quiz;

import DAO.MultipleAnswerUnorderedResponseQuestionDao;
import database.DatabaseConnection;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import user.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuizAttemptTest extends TestCase {
    private static Connection conn;
    private static QuizAttempt at1;
    private static QuizAttempt at2;
    private static Timestamp timestamp ;
    private static double score = 6.0;
    private static long userId = 1;
    private static long atId = 1;

    @Before
    public  void init() throws ClassNotFoundException, SQLException, IOException {
        conn= DatabaseConnection.getConnection();
        DatabaseConnection.resetTables();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Test
    public void testAttempts() throws SQLException {
        at1 = new QuizAttempt(userId, score, timestamp);
        at2 = new QuizAttempt(atId, userId, score, timestamp);
        assertEquals(at1.getScore(), score);
        assertEquals(at1.getTimestamp(), timestamp );
        assertEquals(at1.getUserId(), userId);
        at1.setId(atId);
        assertEquals(at1.getId(), atId);

        assertEquals(at2.getScore(), score);
        assertEquals(at2.getTimestamp(), timestamp );
        assertEquals(at2.getUserId(), userId);
        assertEquals(at2.getId(), atId);
    }


}