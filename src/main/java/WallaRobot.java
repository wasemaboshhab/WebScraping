import java.util.Map;

public class WallaRobot extends BaseRobot {

    public WallaRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }


    @Override
    public Map<String, Integer> getWordsStatistics() {
        return null;
    }

    @Override
    public int countInArticlesTitles(String text) {
//  כמה פעמים המחרוזת מופיעה בכותרות או כתרות משנה בעמוד הבית
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        //scrap to all the articles .
        // read the text of each article. : count the words.
        // return the LongestArticleTitle.


        return null;
    }
}
