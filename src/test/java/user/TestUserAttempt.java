package user;

import junit.framework.TestCase;
import user.UserAttempt;

import java.sql.Timestamp;

public class TestUserAttempt extends TestCase {

    public void test1() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserAttempt ua = new UserAttempt(2, 4, 5, 95, timestamp);
        assertEquals(ua.getId(), 2);
        assertEquals(ua.getQuizId(), 4);
        assertEquals(ua.getUserId(), 5);
        assertEquals(ua.getScore(), 95, 0.01);
        assertEquals(ua.getTimeStamp(), timestamp);
        assertEquals(ua.toString(), "UserAttempt{" +
                "id=" + 2 +
                ", quizId=" + 4 +
                ", userId=" + 5 +
                ", score=" + 95.0 +
                ", timestamp=" + timestamp +
                '}');
    }
}
