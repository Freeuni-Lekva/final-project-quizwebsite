package user;

import java.sql.Date;

public class UserAttempt {

    private int id;
    private int quizId;
    private int userId;
    private int score;
    private Date date;

    public UserAttempt(int id, int quizId, int userId, int score, Date date) {
        this.id = id;
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public UserAttempt(int quizId, int userId, int score, Date date) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
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
