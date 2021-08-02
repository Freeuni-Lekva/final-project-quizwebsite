package quiz;

import question.Question;
import user.User;

import java.util.List;

public abstract class Quiz {
    protected List<Question> questions;
    private boolean correctImmediately;
    private User author;
    private String name;
    private long id;

    public Quiz(long id, List<Question> questions, boolean correctImmediately, User author, String name) {
        this.questions = questions;
        this.correctImmediately = correctImmediately;
        this.author = author;
        this.name = name;
        this.id = id;
    }

    public Quiz(List<Question> questions, boolean correctImmediately, User author, String name) {
        this.questions = questions;
        this.correctImmediately = correctImmediately;
        this.author = author;
        this.name = name;
    }

    public boolean correctImmediately() { return correctImmediately; };

    public abstract List<Question> getQuestions();
}
