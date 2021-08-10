import java.util.*;


public class Main {
    public static void main(String[] args) {


        while (true) {
            websites();
            switch (userChoice()) {
                case Def.MAKO:
                    makoGame();
                    break;
                case Def.WALLA:
                    wallaGame();
                    break;
                case Def.YNET:
                    YnetRobot ynet = new YnetRobot(Def.YNET_URL);
                    System.out.println(ynet.getWebsiteDocument().title());
                    break;
                default:
                    System.out.println("invalid!");
            }

        }









    }
    private static void wallaGame() {
        System.out.println("\t\tCOLLECT POINTS!!");
        System.out.println("Loading...");
        Scanner scanner = new Scanner(System.in);
        WallaRobot walla = new WallaRobot(Def.WALLA_URL);
        int userPoints = 0; String userGuesses = "";
        Map<String, Integer> wordsStatistics = walla.getWordsStatistics();
        System.out.println("Guess what the most FIVE!  common words is\n!!As a hint here is the longest article\n\t\t > " + walla.getLongestArticleTitle() + " < ");
        for (int i = 0; i < Def.FIVE_GUESSES; i++) {
            System.out.println(i+1 + ": ");
            userGuesses = scanner.next();
            if (wordsStatistics.containsKey(userGuesses)) {
                userPoints += wordsStatistics.get(userGuesses);
            }
        }
        int realTextAppears = walla.countInArticlesTitles(shortTextFromUser());
        System.out.println("How many times you think its appears ?\n>");
        int userThinkItAppears = userThinkThatTextAppear();
        if (realTextAppears == userThinkItAppears + 1 || userThinkItAppears - 1 == realTextAppears||
                realTextAppears == userThinkItAppears + 2 || userThinkItAppears - 2 == realTextAppears){
            userPoints += Def.PRICE_FOR_GOOD_GUESS;}
        System.out.println("\n\t\t > Point Collected : " + userPoints + " <");



    }
    private static void makoGame() {
        Scanner scanner = new Scanner(System.in);
        MakoRobot mako = new MakoRobot(Def.MAKO_URL);
        int userPoints = 0; String userGuesses = "";
        System.out.println("\t\tCOLLECT POINTS!!");
        System.out.println("Loading...");
        Map<String, Integer> wordsStatistics = mako.getWordsStatistics();
        System.out.println("Guess what the most FIVE!  common words is\n!!As a hint here is the longest article\n\t\t > " + mako.getLongestArticleTitle() + " < ");
        for (int i = 0; i < Def.FIVE_GUESSES; i++) {
            System.out.println(i+1 + ": ");
            userGuesses = scanner.next();
            if (wordsStatistics.containsKey(userGuesses)) {
                userPoints += wordsStatistics.get(userGuesses);
            }
        }
        int realTextAppears = mako.countInArticlesTitles(shortTextFromUser());
        System.out.println("How many times you think its appears ?\n>");
        int userThinkItAppears = userThinkThatTextAppear();
        if (realTextAppears == userThinkItAppears + 1 || userThinkItAppears - 1 == realTextAppears||
                realTextAppears == userThinkItAppears + 2 || userThinkItAppears - 2 == realTextAppears){
            userPoints += Def.PRICE_FOR_GOOD_GUESS;}
        System.out.println("\n\t\t > Point Collected : " + userPoints + " <");

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
