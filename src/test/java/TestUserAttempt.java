import junit.framework.TestCase;
import user.UserAttempt;

import java.sql.Date;

public class TestUserAttempt extends TestCase {

    public void test1() {
        Date date = new Date(1, 4, 6);
        UserAttempt ua = new UserAttempt(2, 4, 5, 95, date);
        assertEquals(ua.getId(), 2);
        assertEquals(ua.getQuizId(), 4);
        assertEquals(ua.getUserId(), 5);
        assertEquals(ua.getScore(), 95);
        assertEquals(ua.getDate(), date);
        assertEquals(ua.toString(), "UserAttempt{" +
                "id=" + 2 +
                ", quizId=" + 4 +
                ", userId=" + 5 +
                ", score=" + 95 +
                ", date=" + date +
                '}');
    }
}
