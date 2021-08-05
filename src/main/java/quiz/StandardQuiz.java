package quiz;

import question.Question;
import user.User;

import java.util.List;
import java.util.SortedMap;

public class StandardQuiz extends Quiz{
    public StandardQuiz(long id, List<Question> questions, User author, String name, SortedMap<Integer, User> history) {
        super(id, questions, author, name, history);
    }

    public StandardQuiz(List<Question> questions, User author, String name) {
        super(questions, author, name);
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
