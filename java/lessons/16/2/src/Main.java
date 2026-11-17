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
            }
        }
        scanner.close();
    }

    private static boolean checkName(String name) {
        String[] words = name.trim().split("\\s+");
        return words.length == 3;
    }
}
