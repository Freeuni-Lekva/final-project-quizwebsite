package question;

import DAO.QuestionDao;
import response.Response;

import java.sql.SQLException;

public interface Question {
    String getQuestionText();
    double getScore(Response response);
    QuestionDao getDao() throws SQLException, ClassNotFoundException;
}
