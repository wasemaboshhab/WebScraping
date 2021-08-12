
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MakoRobot   extends BaseRobot   {


    public MakoRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
        foundUrlTitlesAndMainArticle();
    }

    private void foundUrlTitlesAndMainArticle() {
        Elements specialSlider = this.getWebsiteDocument().getElementsByClass("teasers");
        Elements specialArticlesElements = specialSlider.get(0).getElementsByClass("slider_image_inside");
        for (Element currentElement : specialArticlesElements) {
            String currentUrlArticle = currentElement.getElementsByTag("a").attr("href");
            checkIntegrityUrlAndAddTOList(currentUrlArticle);
                Def.MAKO_ARTICLES_TITLE.add(currentElement.text());
        }

        Elements containerNewsElements = this.getWebsiteDocument().getElementsByClass("mako_main_portlet_group_container_td side_bar_width");
        Elements secondaryNews = containerNewsElements.get(0).getElementsByTag("h5");
        for (Element currentElement : secondaryNews) {
            Def.MAKO_ARTICLES_TITLE.add(currentElement.text());
            String currentUrlTitle = currentElement.parent().getElementsByTag("a").attr("href");
            checkIntegrityUrlAndAddTOList(currentUrlTitle);
        }




    }
    private void checkIntegrityUrlAndAddTOList(String link) {
        if (link.contains("https:")) {
            Def.URL_MAKO_ARTICLES.add(link);
        } else {
            Def.URL_MAKO_ARTICLES.add("https://www.mako.co.il/" + link);
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
    private String[] collectWords() {
        String space = " ";
        Elements mainParagraphElement;
        Elements titleElement;
        String allTheWord = "";
        for (int i = 0; i < Def.URL_MAKO_ARTICLES.size(); i++) {
            Document currentArticleDocument = joinWebSite(Def.URL_MAKO_ARTICLES.get(i));
            try {
                titleElement = currentArticleDocument.getElementsByTag("h1");
                mainParagraphElement = currentArticleDocument.getElementsByTag("h2");
                String titleText = titleElement.get(0).text();
                String mainParagraphText = mainParagraphElement.get(0).text();
                allTheWord += titleText + space + mainParagraphText;
            } catch (Exception e) { }


            Elements partsOfArticle = currentArticleDocument.getElementsByTag("p");
            for (Element currentPart : partsOfArticle) {
                String currentParagraphText = currentPart.text();
                allTheWord += space + currentParagraphText;
            }
        }


        String[] words;
        words = allTheWord.split(" ");
        return words;
    }




    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> wordsStatistics = new HashMap<>();
        for (String currentWord : collectWords()) {
            Integer incidenceWord = wordsStatistics.get(currentWord);
            if (incidenceWord == null) incidenceWord = 0;
            wordsStatistics.put(currentWord, ++incidenceWord);
        }
        return wordsStatistics;
    }

    @Override
    public int countInArticlesTitles(String text) {
        int counter = 0;
        for (String currentArticleTitle : Def.MAKO_ARTICLES_TITLE)
            if (currentArticleTitle.contains(text)){     counter++;}
        return counter;
    }

    @Override
    public String getLongestArticleTitle() {
        String space = " ";
        Elements mainParagraphElement;
        Elements titleElement;
        int longestArticleIndex = 0;

        int tempLongestArticle = 0;
        for (int i = 0; i < Def.URL_MAKO_ARTICLES.size(); i++) {
            Document currentTitleDocument = joinWebSite(Def.URL_MAKO_ARTICLES.get(i));
            String currentArticleText = "";
            try {
                titleElement = currentTitleDocument.getElementsByTag("h1");
                mainParagraphElement = currentTitleDocument.getElementsByTag("h2");
                String title = titleElement.get(0).text();
                String mainParagraph = mainParagraphElement.get(0).text();
                currentArticleText = title + space + mainParagraph;
            } catch (Exception e) { }
            Elements articlePartsElements = currentTitleDocument.getElementsByTag("p");
            for (Element currentParagraphElement : articlePartsElements) {
                String currentParagraphText = currentParagraphElement.text();
                currentArticleText += space + currentParagraphText;
            }
            if (tempLongestArticle < currentArticleText.length()) {
                tempLongestArticle = currentArticleText.length();
                longestArticleIndex = i;
            }
        }
        return Def.MAKO_ARTICLES_TITLE.get(longestArticleIndex);
    }

}
