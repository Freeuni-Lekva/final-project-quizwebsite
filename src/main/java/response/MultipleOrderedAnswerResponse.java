package response;

import java.util.ArrayList;
import java.util.Iterator;

public class MultipleOrderedAnswerResponse implements Response{
    private final ArrayList<String> allAnswers;

    public MultipleOrderedAnswerResponse(ArrayList<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    @Override
    public Iterator<String> getAllAnswers() {
        return allAnswers.iterator();
    }
}
