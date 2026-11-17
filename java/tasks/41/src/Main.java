import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] previousPasswords = {"password123", "qwerty123", "letmein123"};
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите новый пароль:");
            while (scanner.hasNextLine()) {
                String password = scanner.nextLine();
                if (isPreviousPassword(password, previousPasswords)) {
                    System.out.println("Этот пароль уже использовался. Введите другой пароль.");
                } else if (password.length() < 8) {
                    System.out.println("Пароль должен содержать не менее 8 символов. Введите пароль ещё раз.");
                } else {
                    System.out.println("Security password verified success");
                    return;
                }
            }
        }
    }

    private static boolean isPreviousPassword(String password, String[] previousPasswords) {
        for (String previousPassword : previousPasswords) {
            if (password.equals(previousPassword)) {
                return true;
            }
        }
        return false;
    }
}
