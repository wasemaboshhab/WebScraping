import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

public class YnetRobot extends BaseRobot{

    public YnetRobot(String rootWebsiteUrl) {

        super(rootWebsiteUrl);
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {


       String text = "This is a string i am a string string";
       String[] words = text.split(" ");
       Map<String, Integer> wordsCount = new HashMap<>();
       for (int i = 0; i < words.length; i++) {
           String word = words[i];
           Integer count = wordsCount.get(word);
           if (count == null) {
               count = 0;
           }
           wordsCount.put(word, count + 1);
       }

        return wordsCount;
    }

    @Override
    public int countInArticlesTitles(String text) {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }
}
