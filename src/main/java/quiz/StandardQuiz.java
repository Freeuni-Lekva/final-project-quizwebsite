package quiz;

import question.Question;
import user.User;

import java.util.List;
import java.util.SortedMap;

public class StandardQuiz extends Quiz{
    public StandardQuiz(long id, List<Question> questions, User author, String name, List<QuizAttempt> history) {
        super(id, questions, author, name, history);
    }

    public StandardQuiz(List<Question> questions, User author, String name, List<QuizAttempt> history) {
        super(questions, author, name, history);
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
