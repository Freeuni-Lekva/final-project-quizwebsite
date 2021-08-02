package question;

import response.Response;

import java.util.ArrayList;

public abstract class OrderedResponseQuestion implements Question{
    private final String questionText;
    protected final ArrayList<String> orderOfAnswers;

    public OrderedResponseQuestion(String questionText, ArrayList<String> orderOfAnswers) {
        this.questionText = questionText;
        this.orderOfAnswers = orderOfAnswers;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public abstract double getScore(Response response);
}
