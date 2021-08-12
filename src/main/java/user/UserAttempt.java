package user;

import java.sql.Date;
import java.sql.Timestamp;

public class UserAttempt {

    private long id;
    private long quizId;
    private long userId;
    private double score;
    private Timestamp timestamp;

    public UserAttempt(long id, long quizId, long userId, double score, Timestamp timestamp) {
        this(quizId, userId, score, timestamp);
        this.id = id;
    }

    public UserAttempt(long quizId, long userId, double score, Timestamp timestamp) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }


    public long getId() {
        return id;
    }

    public long getQuizId() {
        return quizId;
    }

    public long getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    public Timestamp getTimeStamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "UserAttempt{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", userId=" + userId +
                ", score=" + score +
                ", timestamp=" + timestamp +
                '}';
    }
}
