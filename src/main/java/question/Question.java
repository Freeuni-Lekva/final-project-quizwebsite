package question;

import response.Response;

public interface Question {
    String getQuestionText();
    double getScore(Response response);
    //String getHTML();
}
