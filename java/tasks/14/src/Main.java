import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random(12345L);
        int target = rand.nextInt(20) + 1;
        boolean guessed = false;
        int attempts = 0;

        while (attempts < 5 && !guessed) {
            int guess;
            try {
                guess = sc.nextInt();
            } catch (InputMismatchException exception) {
                sc.next();
                System.out.println("Ошибка: введите целое число!");
                continue;
            }

            attempts++;
            if (guess > target) {
                System.out.println("Загаданное мною число меньше.");
            } else if (guess < target) {
                System.out.println("Загаданное мною число больше.");
            } else {
                System.out.println("Совершенно верно! Это и есть загаданное мною число!");
                guessed = true;
            }
        }

        if (!guessed) {
            System.out.println("Конец игры.");
            System.out.println("Мною было загадано число: " + target);
        }
    }
}
