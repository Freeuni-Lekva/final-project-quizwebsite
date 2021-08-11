package question;

import response.Response;

import java.util.HashSet;

public abstract class UnorderedResponseQuestion implements Question{
    private final String questionText;
    protected final HashSet<String> legalAnswers;

    protected UnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers) {
        this.questionText = questionText;
        this.legalAnswers = legalAnswers;
    }


    public String getQuestionText() {
        return questionText;
    }

    public abstract double getScore(Response response);

    public HashSet<String> getLegalAnswers(){
        return  legalAnswers;
    }

}
