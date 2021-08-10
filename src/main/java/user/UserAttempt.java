package user;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class UserAttempt {

    private long id;
    private long quizId;
    private long userId;
    private double score;
    private Timestamp date;

    public UserAttempt(long id, long quizId, long userId, double score, Timestamp date) {
        this.id = id;
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public UserAttempt(long quizId, long userId, double score, Timestamp date) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
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

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "UserAttempt{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", userId=" + userId +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
