import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите имя:");
            if (!scanner.hasNextLine()) {
                return;
            }
            String userName = scanner.nextLine();

            while (true) {
                System.out.println("Введите пароль:");
                if (!scanner.hasNextLine()) {
                    return;
                }
                String password = scanner.nextLine();
                if (password.length() < 8) {
                    System.out.println("Пароль слишком короткий. Минимальная длина — 8 символов.");
                } else if (password.length() > 15) {
                    System.out.println("Пароль слишком длинный. Максимальная длина — 15 символов.");
                } else {
                    System.out.println("Имя пользователя: " + userName);
                    System.out.println("Пароль: " + password);
                    return;
                }
            }
        }
    }
}
