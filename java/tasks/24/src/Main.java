import java.util.Locale;
import java.util.Scanner;

public class Main {
    static String name;
    static int age = 0;
    static double weight = 3.0;
    static int hunger = 0;
    static int play = 0;
    static int menuCount = 0;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        name = sc.nextLine().trim();

        while (sc.hasNextLine()) {
            String command = sc.nextLine().trim();
            hunger = Math.max(0, hunger - 5);
            weight = Math.max(0.0, weight - 0.1);
            menuCount++;
            if (menuCount % 20 == 0) {
                age++;
            }

            switch (command) {
                case "STATE":
                    System.out.println("AGE=" + age);
                    System.out.printf("WEIGHT=%.1f%n", weight);
                    System.out.println("HUNGER=" + hunger);
                    System.out.println("PLAY=" + play);
                    break;
                case "FEED1":
                case "FEED2":
                case "FEED3":
                    if (hunger > 50) {
                        System.out.println("EAT: REFUSE");
                    } else {
                        int food = command.charAt(4) - '0';
                        hunger = Math.min(100, hunger + 20 * (food + 1));
                        weight += 0.2 * food;
                        System.out.println("EAT: OK");
                    }
                    break;
                case "PLAY":
                    if (hunger <= 20 || hunger >= 90) {
                        System.out.println("PLAY: REFUSE_HUNGER");
                    } else if (play == 100) {
                        System.out.println("PLAY: REFUSE_TIRED");
                    } else {
                        play = Math.min(100, play + 50);
                        hunger = Math.max(0, hunger - 60);
                        weight = Math.max(0.0, weight - 0.3);
                        System.out.println("PLAY: OK");
                    }
                    break;
                case "S":
                    return;
                default:
                    break;
            }
        }
    }
}
