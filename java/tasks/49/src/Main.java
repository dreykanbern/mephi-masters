import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final String ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "0123456789";

    private static final String KEY =
            "ZYXWVUTSRQPONMLKJIHGFEDCBA"
            + "zyxwvutsrqponmlkjihgfedcba"
            + "УКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁЙЦ"
            + "укенгшщзхъфывапролджэячсмитьбюёйц"
            + "9876543210";

    public static void main(String[] args) {
        boolean decrypting = args.length == 1 && args[0].equals("decrypt");
        if (args.length != 0 && !decrypting) {
            System.err.println("Для шифрования запустите Main, для расшифровки — Main decrypt.");
            return;
        }

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println(decrypting ? "Введите зашифрованное сообщение" : "Введите сообщение");
        if (!scanner.hasNextLine()) {
            return;
        }
        String message = scanner.nextLine();

        System.out.println(decrypting
                ? "Введите зашифрованное время отправки или нажмите Enter"
                : "Введите время отправки, например 09:30, или нажмите Enter");
        String time = scanner.hasNextLine() ? scanner.nextLine() : "";
        String originalTime = decrypting ? decrypt(time) : time;
        if (!originalTime.isEmpty()) {
            try {
                LocalTime.parse(originalTime);
            } catch (DateTimeParseException exception) {
                System.err.println("Укажите корректное время в 24-часовом формате, например 09:30.");
                return;
            }
        }

        String label = decrypting ? "Расшифрованное" : "Зашифрованное";
        String convertedMessage = decrypting ? decrypt(message) : encrypt(message);
        System.out.println(label + " сообщение: " + convertedMessage);
        if (!time.isEmpty()) {
            String convertedTime = decrypting ? originalTime : encrypt(time);
            System.out.println(label + " время отправки: " + convertedTime);
        }
    }

    public static String encrypt(String text) {
        return substitute(text, ALPHABET, KEY);
    }

    public static String decrypt(String text) {
        return substitute(text, KEY, ALPHABET);
    }

    private static String substitute(String text, String source, String target) {
        StringBuilder result = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            int keyIndex = source.indexOf(character);
            if (keyIndex >= 0) {
                result.append(String.valueOf(character).replace(character, target.charAt(keyIndex)));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}
