import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public abstract class BaseRobot  {
    private String rootWebsiteUrl;
    private Document websiteDocument;

    public BaseRobot(String rootWebsiteUrl) {
        this.rootWebsiteUrl = rootWebsiteUrl;
        try {
            this.websiteDocument = Jsoup.connect(rootWebsiteUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public abstract Map<String, Integer> getWordsStatistics();
//    מיתודה זאת נכנסת לכל הכתבות וסופרת כל המילים וכמה פעמים מופיעה כל מילה בכל הכתבות שיש באתר!


    public abstract int countInArticlesTitles(String text);
//    רק הטקס שמופיע בעמוד הראשי
//    המיתודה לא נכנסת לכתבות סופרת כמה פעמים מופיעה המחרוזת העמוד הראשי בכתרות ובכתרות משנה

    public abstract String getLongestArticleTitle();
//    הבוט יקרא את כל הכתבה!וידע להחזיר את הכתבה שהתוכן שלא ארוך ביותר





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
