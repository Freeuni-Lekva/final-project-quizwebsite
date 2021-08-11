package quiz;

import java.sql.Timestamp;

public class QuizAttempt {
    private long id;
    private final long userId;
    private final double score;
    private final Timestamp timestamp;

    public QuizAttempt(long id, long userId, double score, Timestamp timestamp) {
        this(userId, score, timestamp);
        this.id = id;
    }

    public QuizAttempt(long userId, double score, Timestamp timestamp) {
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
