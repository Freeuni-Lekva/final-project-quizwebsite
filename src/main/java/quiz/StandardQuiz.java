package quiz;

import question.Question;
import user.User;

import java.util.List;

public class StandardQuiz extends Quiz{
    public StandardQuiz(long id, List<Question> questions, boolean correctImmediately, User author, String name) {
        super(id, questions, correctImmediately, author, name);
    }

    public StandardQuiz(List<Question> questions, boolean correctImmediately, User author, String name) {
        super(questions, correctImmediately, author, name);
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
