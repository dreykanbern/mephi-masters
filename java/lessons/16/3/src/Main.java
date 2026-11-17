import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isCorrectName = false;
        while (!isCorrectName && scanner.hasNextLine()) {
            String name = scanner.nextLine();
            isCorrectName = checkName(name);
            if (!isCorrectName) {
                System.out.println("Введите корректное имя!");
            } else {
                System.out.println(formatName(name));
            }
        }
        scanner.close();
    }

    private static boolean checkName(String name) {
        String[] words = name.trim().split("\\s+");
        return words.length == 3;
    }

    private static String formatName(String name) {
        String[] words = name.trim().split("\\s+");
        for (int index = 0; index < words.length; index++) {
            String word = words[index].toLowerCase(Locale.ROOT);
            words[index] = word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
        }
        sortByLength(words);
        return String.join(" ", words);
    }

    private static void sortByLength(String[] words) {
        for (int pass = 0; pass < words.length - 1; pass++) {
            for (int index = 0; index < words.length - 1 - pass; index++) {
                if (words[index].length() > words[index + 1].length()) {
                    String temporary = words[index];
                    words[index] = words[index + 1];
                    words[index + 1] = temporary;
                }
            }
        }
    }
}
