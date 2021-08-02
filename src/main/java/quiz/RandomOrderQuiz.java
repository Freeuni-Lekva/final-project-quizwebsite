package quiz;

import question.Question;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomOrderQuiz extends Quiz{

    public RandomOrderQuiz(long id, List<Question> questions, boolean correctImmediately, User author, String name) {
        super(id, questions, correctImmediately, author, name);
    }

    public RandomOrderQuiz(List<Question> questions, boolean correctImmediately, User author, String name) {
        super(questions, correctImmediately, author, name);
    }

    @Override
    public List<Question> getQuestions() {
        return shuffle(questions);
    }

    private List<Question> shuffle(List<Question> questions) {
        Random random = new Random();
        List<Question> result = new ArrayList<>();
        while(questions.size() > 0) {
            int index = random.nextInt(questions.size() - 1);
            result.add(questions.get(index));
            questions.remove(index);
        }
        return result;
    }
}
