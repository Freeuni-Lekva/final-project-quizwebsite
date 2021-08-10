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

    public ArrayList<String> getOrderOfAnswers(){
        return orderOfAnswers;
    }



    @Override
    public abstract double getScore(Response response);
}
