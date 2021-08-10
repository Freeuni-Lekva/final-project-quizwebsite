package question;

import DAO.MultipleAnswerUnorderedResponseQuestionDao;
import DAO.QuestionDao;
import database.DatabaseConnection;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class MultipleAnswerUnorderedResponseQuestion extends UnorderedResponseQuestion {
    private final int numOfRequestedAnswers;

    public MultipleAnswerUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers, int numOfRequestedAnswers) {
        super(questionText, legalAnswers);
        this.numOfRequestedAnswers = numOfRequestedAnswers;
    }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        int score = 0;
        while (iterator.hasNext()) {
            if (legalAnswers.contains(iterator.next())) score++;
        }
        return (double) score / numOfRequestedAnswers;
    }

    @Override
    public QuestionDao getDao() throws SQLException, ClassNotFoundException {
        return new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
    }

    public int getNumOfRequestedAnswers(){
        return numOfRequestedAnswers;
    }
}
