package response;

import java.util.HashSet;
import java.util.Iterator;

public class MultipleUnorderedAnswerResponse implements Response {
    private final HashSet<String> allAnswers;

    public MultipleUnorderedAnswerResponse(HashSet<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    @Override
    public Iterator<String> getAllAnswers() {
        return allAnswers.iterator();
    }
}
