package question;

import response.Response;

import java.util.HashSet;
import java.util.Iterator;

public class MultipleChoiceUnorderedResponseQuestion extends UnorderedResponseQuestion {
    private final HashSet<String> choices;

    public MultipleChoiceUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers, HashSet<String> choices) {
        super(questionText, legalAnswers);
        this.choices = choices;
        if (!isLegalAnswersValid()) throw new RuntimeException("legalAnswers isn't valid");

    }

    @Override
    public double getScore(Response response) {
        if (!isResponseValid(response)) throw new RuntimeException("response isn't valid");
        Iterator<String> iterator = response.getAllAnswers();
        int count = 0;
        while (iterator.hasNext()) {
            if (legalAnswers.contains(iterator.next())) count++;
        }
        return (double) count / legalAnswers.size();
    }

    public HashSet<String> getChoices() {
        return choices;
    }

    private boolean isLegalAnswersValid() {
        for (String ans : legalAnswers) {
            if (!choices.contains(ans))
                return false;
        }
        return true;
    }

    private boolean isResponseValid(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        while (iterator.hasNext()) {
            if (!choices.contains(iterator.next()))
                return false;
        }
        return true;
    }


}

