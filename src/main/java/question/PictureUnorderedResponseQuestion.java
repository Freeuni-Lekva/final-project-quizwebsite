package question;

import DAO.PictureUnorderedResponseQuestionDao;
import DAO.QuestionDao;
import database.DatabaseConnection;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class PictureUnorderedResponseQuestion extends UnorderedResponseQuestion {
    private final String picUrl;

    public PictureUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers, String picUrl) {
        super(questionText, legalAnswers);
        this.picUrl = picUrl;
    }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        if (legalAnswers.contains(iterator.next())) return 1;

        return 0;
    }

    @Override
    public QuestionDao getDao() throws SQLException, ClassNotFoundException {
        return new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
    }

    public String getPicUrl() {
        return picUrl;
    }
}
