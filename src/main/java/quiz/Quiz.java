package quiz;

import question.Question;
import user.User;

import java.util.List;
import java.util.SortedMap;

public abstract class Quiz {
    protected List<Question> questions;
    private final User author;
    private final String name;
    private long id;
    private List<QuizAttempt> history;

    public Quiz(long id, List<Question> questions, User author, String name, List<QuizAttempt> history) {
        this(questions, author, name, history);
        this.id = id;
    }

    public Quiz(List<Question> questions, User author, String name, List<QuizAttempt> history) {
        this.questions = questions;
        this.author = author;
        this.name = name;
        this.history = history;
    }

    public User getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public List<QuizAttempt> getHistory() { return history; }

    public abstract List<Question> getQuestions();
}
