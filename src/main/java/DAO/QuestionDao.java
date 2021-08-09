package DAO;

import question.Question;
import quiz.Quiz;

import java.util.List;

public interface QuestionDao {
    void addQuestion(Question question);
    List<Question> getQuestions(int quizId);
}
