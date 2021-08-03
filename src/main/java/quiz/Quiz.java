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
    SortedMap<Integer, User> history;

    public Quiz(long id, List<Question> questions, User author, String name, SortedMap<Integer, User> history) {
        this.questions = questions;
        this.author = author;
        this.name = name;
        this.id = id;
        this.history = history;
    }

    public Quiz(List<Question> questions, User author, String name) {
        this.questions = questions;
        this.author = author;
        this.name = name;
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

    public abstract List<Question> getQuestions();
}
