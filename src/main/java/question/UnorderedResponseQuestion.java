package question;

import response.Response;

import java.util.HashSet;

public abstract class UnorderedResponseQuestion implements Question{
    private final String questionText;
    protected final HashSet<String> legalAnswers;
    private  int quiz_id;

    protected UnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers) {
        this.questionText = questionText;
        this.legalAnswers = legalAnswers;
    }

    public void setQuizId(int quiz_id){
        this.quiz_id =  quiz_id;
    }

    public int getQuizId() {
        return quiz_id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public abstract double getScore(Response response);

    public HashSet<String> getLegalAnswers(){
        return  legalAnswers;
    }

}
