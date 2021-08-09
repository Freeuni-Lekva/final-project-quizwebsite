package question;

import response.Response;

import java.util.ArrayList;

public abstract class OrderedResponseQuestion implements Question{
    private final String questionText;
    protected final ArrayList<String> orderOfAnswers;
    private int quiz_id;


    public OrderedResponseQuestion(String questionText, ArrayList<String> orderOfAnswers) {
        this.questionText = questionText;
        this.orderOfAnswers = orderOfAnswers;
    }

    public int getQuizId() {
        return quiz_id;
    }
    public void setQuizId(int quiz_id){
        this.quiz_id=quiz_id;
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
