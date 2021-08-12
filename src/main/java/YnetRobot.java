import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YnetRobot extends BaseRobot{

    public YnetRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
        findMainArticle();
        findSpecialArticles();
        findSmallArticles();
        // Advertising---> no title and article
        Def.URL_YNET_ARTICLES.remove(81);
        Def.YNET_ARTICLES_TITLE.remove(81);
    }

    private void findMainArticle() {
        Elements container = this.getWebsiteDocument().getElementsByClass("textDiv");
        Element mainArticleElement = container.get(0);
        Elements titleElement = mainArticleElement.getElementsByTag("h1");
        String titleText = titleElement.get(0).text();
        Elements mainBodyArticleElement = mainArticleElement.getElementsByClass("slotSubTitle");
        String mainBodyText = mainBodyArticleElement.text();
        String titleAndMainBody = titleText + " " + mainBodyText;
        String urlMainArticle = mainArticleElement.child(0).getElementsByTag("a").attr("href");
        Def.URL_YNET_ARTICLES.add(urlMainArticle);
        Def.YNET_ARTICLES_TITLE.add(titleAndMainBody);
    }
    private  void findSpecialArticles() {
        Elements specialArticlesElement = this.getWebsiteDocument().getElementsByClass("slotTitle medium");
        for (Element currentArticleElement : specialArticlesElement) {
            String urlSpecialArticles = currentArticleElement.parent().getElementsByTag("a").attr("href");
            String articlesTitle = currentArticleElement.parent().text();
            Def.URL_YNET_ARTICLES.add(urlSpecialArticles);
            Def.YNET_ARTICLES_TITLE.add(articlesTitle);
        }

    }
    private void findSmallArticles() {
        Elements smallArticles = this.getWebsiteDocument().getElementsByClass("slotTitle small");
        for (Element element : smallArticles) {
            Def.YNET_ARTICLES_TITLE.add(element.text());
            Def.URL_YNET_ARTICLES.add(element.child(0).attr("href"));
        }
    }
    private Document joinWebSite(String link) {
        Document webSiteDocument = null;
        try {
            webSiteDocument = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webSiteDocument;
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> wordStatistics = new HashMap<>();
        String allWordsInYnet = "";
        for (int i = 0; i < Def.URL_YNET_ARTICLES.size(); i++) {
            Document currentArticleDocument = joinWebSite(Def.URL_YNET_ARTICLES.get(i));
            String mainTitleText = "";
            try {
                Elements mainTitle = currentArticleDocument.getElementsByTag("h1");
                mainTitleText = mainTitle.get(0).text();
                Elements subTitle = currentArticleDocument.getElementsByTag("h2");
                String subTitleText = subTitle.get(0).text();
                allWordsInYnet += mainTitleText + " " + subTitleText;
            } catch (IndexOutOfBoundsException e) {
                Elements mainTitle = currentArticleDocument.getElementsByTag("h1");
                mainTitleText = mainTitle.get(0).text();
                Elements subTitle = currentArticleDocument.getElementsByTag("h3");
                String subTitleText = subTitle.get(0).text();
                allWordsInYnet += mainTitleText + " " + subTitleText;
            }
            Elements articleBody = currentArticleDocument.getElementsByClass("text_editor_paragraph rtl");
            for (Element e : articleBody)
                allWordsInYnet += " " + e.text();
        }
        String[] words = allWordsInYnet.split(" ");
        for (String currentWord : words) {
            Integer incidenceWord = wordStatistics.get(currentWord);
            if (incidenceWord == null) incidenceWord = 0;
            wordStatistics.put(currentWord, ++incidenceWord);
        }
        System.out.println();
        return wordStatistics;
    }
    @Override
    public int countInArticlesTitles(String text) {
        int counter = 0;
        for (String currentArticleTitle : Def.YNET_ARTICLES_TITLE)
            if (currentArticleTitle.contains(text)){  counter++;}
        return counter;
    }
    @Override
    public String getLongestArticleTitle() {
        String longestArticle = "-";
        int longestArticleIndex = 0;
        for (int i = 0; i < Def.URL_YNET_ARTICLES.size(); i++) {
            Document currentArticleDocument = joinWebSite(Def.URL_YNET_ARTICLES.get(i));
            String currentArticleText = "";
            Elements articleBody;
            try {
                Elements mainTitle = currentArticleDocument.getElementsByTag("h1");
                String mainTitleText = mainTitle.get(0).text();
                Elements subTitle = currentArticleDocument.getElementsByTag("h2");
                String subTitleText = subTitle.get(0).text();
                 currentArticleText = mainTitleText + " " + subTitleText;
            } catch (IndexOutOfBoundsException e) {
                Elements mainTitle = currentArticleDocument.getElementsByTag("h1");
                String mainTitleText = mainTitle.get(0).text();
                Elements subTitle = currentArticleDocument.getElementsByTag("h3");
                String subTitleText = subTitle.get(0).text();
                currentArticleText = mainTitleText + " " + subTitleText;
            }
            articleBody = currentArticleDocument.getElementsByClass("text_editor_paragraph rtl");
            for (Element e : articleBody)
                currentArticleText += " " + e.text();
            if (longestArticle.length() < currentArticleText.length()) {
                longestArticle = currentArticleText;
                longestArticleIndex = i;
            }
        }
        return Def.YNET_ARTICLES_TITLE.get(longestArticleIndex);
    }
}
