import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите слово:");
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (!word.matches("\\S+")) {
                    System.out.println("Введите корректное имя!");
                } else {
                    System.out.println("Слово наоборот - " + reverseWord(word));
                    return;
                }
            }
        }
    }

    private static String reverseWord(String word) {
        char[] letters = word.toCharArray();
        for (int left = 0, right = letters.length - 1; left < right; left++, right--) {
            char temporary = letters[left];
            letters[left] = letters[right];
            letters[right] = temporary;
        }
        return new String(letters);
    }
}
