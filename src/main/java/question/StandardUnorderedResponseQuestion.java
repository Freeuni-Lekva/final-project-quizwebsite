package question;

import DAO.QuestionDao;
import DAO.StandardUnorderedResponseQuestionDao;
import database.DatabaseConnection;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class StandardUnorderedResponseQuestion extends UnorderedResponseQuestion {
    public StandardUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers) {
        super(questionText, legalAnswers);
    }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        while (iterator.hasNext()) {
            if (legalAnswers.contains(iterator.next())) return 1;
        }
        return 0;
    }

    @Override
    public QuestionDao getDao() throws SQLException, ClassNotFoundException {
        return new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
    }
}
