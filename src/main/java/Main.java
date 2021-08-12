import java.util.*;


public class Main {
    public static void main(String[] args) {
        int pointsCollectedByUser, realTextAppears;


        while (true) {
            websites();

            switch (userChoice()) {
                case Def.MAKO:
                    sayLoading();
                    MakoRobot mako = new MakoRobot(Def.MAKO_URL);
                    System.out.println("Guess what the most FIVE!  common words is\n!!As a hint: the longest article is\n\t\t > " + mako.getLongestArticleTitle() + " <\nLoading...");
                    pointsCollectedByUser = guessWordToCollectPoints(mako.getWordsStatistics());
                    realTextAppears = mako.countInArticlesTitles(shortTextFromUser());
                    pointsCollectedByUser = givePriceForGoodGuess(realTextAppears, pointsCollectedByUser);
                    System.out.println("\n\t\t > Point Collected : " + pointsCollectedByUser + " <");
                    break;
                case Def.WALLA:
                    sayLoading();
                    WallaRobot walla = new WallaRobot(Def.WALLA_URL);
                    System.out.println("Guess what the most FIVE!  common words is\n!!As a hint: the longest article is\n\t\t > " + walla.getLongestArticleTitle() + " <\nLoading...");
                    pointsCollectedByUser = guessWordToCollectPoints(walla.getWordsStatistics());
                    realTextAppears = walla.countInArticlesTitles(shortTextFromUser());
                    pointsCollectedByUser = givePriceForGoodGuess(realTextAppears, pointsCollectedByUser);
                    System.out.println("\n\t\t > Point Collected : " + pointsCollectedByUser + " <");
                    break;
                case Def.YNET:
                   sayLoading();
                    YnetRobot ynet = new YnetRobot(Def.YNET_URL);
                    System.out.println("Guess what the most FIVE!  common words is\n!!As a hint here: the longest article is\n\t\t > " + ynet.getLongestArticleTitle() + " <\nLoading...");
                    pointsCollectedByUser = guessWordToCollectPoints(ynet.getWordsStatistics());
                    realTextAppears = ynet.countInArticlesTitles(shortTextFromUser());
                    pointsCollectedByUser = givePriceForGoodGuess(realTextAppears, pointsCollectedByUser);
                    System.out.println("\n\t\t > Point Collected : " + pointsCollectedByUser + " <");
                    break;
                default:
                    System.out.println("invalid!");
            }
        }
    }

    private static int givePriceForGoodGuess(int realTextAppears, int pointsCollectedByUser) {
        System.out.println("How many times you think its appears ?\n>");
        int userThinkItAppears = userThinkThatTextAppear();
        if (realTextAppears + 1 == userThinkItAppears || realTextAppears - 1 == userThinkItAppears ||
                realTextAppears + 2 == userThinkItAppears || realTextAppears - 2 == userThinkItAppears) {
            pointsCollectedByUser += Def.PRICE_FOR_GOOD_GUESS;
        }
        return pointsCollectedByUser;
    }
    private static int guessWordToCollectPoints(Map<String, Integer> wordsStatistics) {
        Scanner scanner = new Scanner(System.in);
        int userPoints = 0;
        String userGuesses = "";
        for (int i = 0; i < Def.FIVE_GUESSES; i++) {
            System.out.println(i + 1 + ": ");
            userGuesses = scanner.next();
            if (wordsStatistics.containsKey(userGuesses)) {
                userPoints += wordsStatistics.get(userGuesses);
            }
            System.out.println("\t\t\t YOUR POINTS: " + userPoints);
        }
        return userPoints;
    }
    private static void sayLoading() {
        System.out.println("loading...");
    }
    private static String shortTextFromUser() {
        Scanner inputText = new Scanner(System.in);
        String userText = "";
        do {
            System.out.println("Enter a text between 1-20 characters you think it appear in articles Title");
            userText = inputText.nextLine();
        } while (userText.length() > 20);
        return userText;
    }
    private static int userThinkThatTextAppear () {
        Scanner scanner = new Scanner(System.in);
        int userTextAppear = 0;
        try {
              userTextAppear = scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return userTextAppear;
    }
    private static void websites() {
        System.out.println("\t\tCOLLECT POINTS BY GUESSES");
        System.out.println("    Website List");
        System.out.println("1-Mako");
        System.out.println("2-Walla");
        System.out.println("3-Ynet");
    }
    private static int userChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">");
        int userWebsite = -1;
        try {
             userWebsite = scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return userWebsite;
    }
}
