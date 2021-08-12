package quiz;

import question.Question;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;

public class RandomOrderQuiz extends Quiz{

    public RandomOrderQuiz(long id, List<Question> questions, User author, String name,  List<QuizAttempt> history) {
        super(id, questions, author, name, history);
    }

    public RandomOrderQuiz(List<Question> questions, User author, String name,  List<QuizAttempt> history) {
        super(questions, author, name, history);
    }

    @Override
    public List<Question> getQuestions() {
        return shuffle(questions);
    }

    private List<Question> shuffle(List<Question> questions) {
        Random random = new Random();
        List<Question> result = new ArrayList<>();
        while(questions.size() > 1) {
            int index = random.nextInt(questions.size() - 1);
            result.add(questions.get(index));
            questions.remove(index);
        }
        if (!questions.isEmpty()) result.add(questions.get(0));
        return result;
    }
}
