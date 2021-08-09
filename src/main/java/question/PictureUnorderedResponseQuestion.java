package question;

import response.Response;

import java.util.HashSet;
import java.util.Iterator;

public class PictureUnorderedResponseQuestion extends UnorderedResponseQuestion {
    private final String picUrl;

    public PictureUnorderedResponseQuestion(String questionText, HashSet<String> legalAnswers, String picUrl) {
        super(questionText, legalAnswers);
        this.picUrl = picUrl;
    }



    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        if (legalAnswers.contains(iterator.next())) return 1;
        return 0;
    }

    public String getPicUrl() {
        return picUrl;
    }
}
