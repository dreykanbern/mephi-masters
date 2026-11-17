import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите адрес электронной почты:");
            while (scanner.hasNextLine()) {
                String email = scanner.nextLine().trim();
                String domain = getDomain(email);
                if (domain == null) {
                    System.out.println("Введите корректный адрес электронной почты!");
                } else {
                    System.out.println(domain);
                    return;
                }
            }
        }
    }

    private static String getDomain(String email) {
        if (!email.matches("[^\\s@]+@[^\\s@]+")) {
            return null;
        }
        return email.substring(email.indexOf('@') + 1);
    }
}
