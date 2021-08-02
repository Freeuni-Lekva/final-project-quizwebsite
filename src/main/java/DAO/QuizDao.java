package DAO;

import quiz.Quiz;
import user.User;

import java.util.List;

public interface QuizDao {
    void addQuiz(Quiz quiz);
    List<Quiz> getQuizzes(User author);
    void removeQuiz(Quiz quiz);
}
