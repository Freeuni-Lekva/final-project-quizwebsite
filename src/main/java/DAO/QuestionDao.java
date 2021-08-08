package DAO;

import question.Question;
import quiz.Quiz;

import java.util.List;

public interface QuestionDao {
    void addQuestion(Question question, int quiz_id);
    List<Question> getQuestions(int quizId);
}
