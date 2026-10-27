import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int randInt = rand.nextInt(10) + 1;
        Scanner console = new Scanner(System.in);

        while (true) {
            System.out.print("Введите число: ");
            int guess = console.nextInt();
            if (guess == randInt) {
                System.out.println("Совершенно верно! Это и есть загаданное мною число!");
                break;
            } else if (guess > randInt) {
                System.out.println("Загаданное мною число меньше.");
            } else {
                System.out.println("Загаданное мною число больше.");
            }
        }
    }
}
