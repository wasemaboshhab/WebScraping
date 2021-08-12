import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Map;

public abstract class BaseRobot  {
    private String rootWebsiteUrl;
    private Document websiteDocument;

    public BaseRobot(String rootWebsiteUrl) {
        this.rootWebsiteUrl = rootWebsiteUrl;
        try {
            this.websiteDocument = Jsoup.connect(rootWebsiteUrl).get();
        } catch (IOException e) { e.printStackTrace();}
    }

    public abstract Map<String, Integer> getWordsStatistics();
    public abstract int countInArticlesTitles(String text);
    public abstract String getLongestArticleTitle();





    public String getRootWebsiteUrl() {
        return rootWebsiteUrl;
    }
    public void setRootWebsiteUrl(String rootWebsiteUrl) {
        this.rootWebsiteUrl = rootWebsiteUrl;
    }

    public Document getWebsiteDocument() {
        return websiteDocument;
    }
    public void setWebsiteDocument(Document websiteDocument) {
        this.websiteDocument = websiteDocument;
    }
}
