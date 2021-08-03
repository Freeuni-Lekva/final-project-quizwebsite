package question;

import response.Response;

import java.util.HashSet;
import java.util.Iterator;

public class StandardUnorderedResponseQuestion extends UnorderedResponseQuestion {
    public StandardUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers) {
        super(questionText, legalAnswers);
    }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        if (legalAnswers.contains(iterator.next())) return 1;
        return 0;
    }
}
