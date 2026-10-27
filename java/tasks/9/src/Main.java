import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String signal = scanner.nextLine();

        switch (signal) {
            case "красный":
                System.out.println("остановиться");
                break;
            case "желтый":
                System.out.println("подождать");
                break;
            case "зеленый":
                System.out.println("ехать");
                break;
        }
    }
}
