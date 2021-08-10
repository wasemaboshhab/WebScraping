import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WallaRobot extends BaseRobot {
    public WallaRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
        findUrlAndArticlesTitles();
    }

    private void findUrlAndArticlesTitles() {
        findMainArticle();
        findSpecialArticles();
        findMoreArticles();


    }
        private void findSpecialArticles() {
        Elements specialNewsElements = this.getWebsiteDocument().getElementsByClass("main-taste");
        Elements articlesTitleElements = specialNewsElements.get(0).getElementsByTag("h3");
        for (Element currentArticleTitleElement : articlesTitleElements) {
            Def.WALLA_ARTICLES_TITLE.add(currentArticleTitleElement.text());
            Def.URL_WALLA_ARTICLES.add(currentArticleTitleElement.parent().parent().parent().attr("href"));
        }

    }
        private void findMainArticle() {
        Elements mainArticleElement = this.getWebsiteDocument().getElementsByClass("with-roof ");
        Elements titleElement = mainArticleElement.get(0).getElementsByTag("h2");
        Elements mainBodyElement = mainArticleElement.get(0).getElementsByTag("p");
        String titleAndMainBodyArticle = titleElement.get(0).text() + "" + mainBodyElement.get(0).text();
        Def.WALLA_ARTICLES_TITLE.add(titleAndMainBodyArticle);

        String urlMainArticle = mainArticleElement.get(0).getElementsByTag("a").attr("href");
        Def.URL_WALLA_ARTICLES.add(urlMainArticle);

    }
        private void findMoreArticles() {
            Elements moreArticles = this.getWebsiteDocument().getElementsByClass("all-verticals top");
            Elements h3Tags = moreArticles.get(0).getElementsByTag("h3");
            for (Element currentElement : h3Tags) {
                Def.WALLA_ARTICLES_TITLE.add(currentElement.text());
                String urlArticles = currentElement.parent().parent().parent().getElementsByTag("a").attr("href");
                Def.URL_WALLA_ARTICLES.add(urlArticles);
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
        Map<String, Integer> wordsStatistics = new HashMap<>();
        for (String currentWord : collectAllWordsInWalla()) {
            Integer incidenceWord = wordsStatistics.get(currentWord);
            if (incidenceWord ==null) incidenceWord = 0;
            wordsStatistics.put(currentWord, ++incidenceWord);
        }
        return wordsStatistics;
    }
        private String[] collectAllWordsInWalla() {
        String space = " ";
        String allWords = "";
        for (int i = 0; i < Def.URL_WALLA_ARTICLES.size(); i++) {
            Document currentArticleDocument = joinWebSite(Def.URL_WALLA_ARTICLES.get(i));
            Elements currentArticleTitleElement = currentArticleDocument.getElementsByTag("h1");
            String currentArticleTitleText = currentArticleTitleElement.get(0).text();
            allWords += currentArticleTitleText + space;
            Elements currentArticleBodyText = currentArticleDocument.getElementsByTag("p");
            for (Element currentPartOfTextElement : currentArticleBodyText) {
                String currentPartOfText = currentPartOfTextElement.text();
                allWords += currentPartOfText + space;
            }

        }
        String[] words;
        words = allWords.split(" ");
            System.out.println();
        return words;
    }
    @Override
    public int countInArticlesTitles(String text) {
//  כמה פעמים המחרוזת מופיעה בכותרות או כתרות משנה בעמוד הבית
        int counter = 0;
        for (String currentArticleTitle : Def.WALLA_ARTICLES_TITLE)
            if (text.contains(currentArticleTitle)) counter++;
        return counter;
    }
    @Override
    public String getLongestArticleTitle() {
        int saveArticleIndex = 0;
        String longArticle = " ";
        for (int i = 0; i < Def.URL_WALLA_ARTICLES.size(); i++) {
            Document currentDocument = joinWebSite(Def.URL_WALLA_ARTICLES.get(i));
            String currentArticleText = "";
            Elements titleElement = currentDocument.getElementsByTag("h1");
            currentArticleText = titleElement.get(0).text();
            Elements articleTextElements = currentDocument.getElementsByTag("p");
            for (Element currentElement : articleTextElements) {
                String currentPartOfText = currentElement.text();
                currentArticleText += currentPartOfText;
            }
            if (longArticle.length() < currentArticleText.length()) {
                longArticle = currentArticleText;
                saveArticleIndex = i ;
            }
        }
        return Def.WALLA_ARTICLES_TITLE.get(saveArticleIndex);
    }
}
