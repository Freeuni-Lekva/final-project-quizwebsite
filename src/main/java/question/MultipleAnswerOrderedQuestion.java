package question;

import DAO.QuestionDao;
import response.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class MultipleAnswerOrderedQuestion extends OrderedResponseQuestion {

    public MultipleAnswerOrderedQuestion(String questionText, ArrayList<String> orderOfAnswers) {
        super(questionText, orderOfAnswers);
    }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        int count = 0;
        for (String ans : orderOfAnswers) {
            if (iterator.hasNext()) {
                if (iterator.next().equals(ans)) count++;
            }
        }
        return (double) count / orderOfAnswers.size();
    }

    @Override
    public QuestionDao getDao() throws SQLException, ClassNotFoundException {
        return null;
    }
}
