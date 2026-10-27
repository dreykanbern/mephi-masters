import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите x: ");
        int x = scanner.nextInt();

        System.out.println("Выражение 1: " + ((true) && (3 > 4)));
        System.out.println("Выражение 2: " + (!(x > 0) && (x > 0)));
        System.out.println("Выражение 3: " + ((x > 0) || (x < 0)));
        System.out.println("Выражение 4: " + ((x != 0) || (x == 0)));
        System.out.println("Выражение 5: " + ((x >= 0) || (x < 0)));
        System.out.println("Выражение 6: " + ((x != 1) == !(x == 1)));
    }
}
