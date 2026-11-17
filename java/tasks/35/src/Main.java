import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String ORIGINAL_TEXT =
            "Жили-были старик со старухой.\n\n"
            + "Вот и говорит старик старухе:\n\n"
            + "— Поди-ка, старуха, по коробу поскреби, по сусеку помети, не наскребешь ли муки на колобок.";

    public static void main(String[] args) {
        String text = ORIGINAL_TEXT;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(text);
            while (true) {
                printMenu();
                if (!scanner.hasNextLine()) {
                    return;
                }
                String command = scanner.nextLine().trim();
                switch (command) {
                    case "1":
                        text = text.replace(" ", "");
                        System.out.println(text);
                        break;
                    case "2":
                        System.out.println("1. Все заглавные");
                        System.out.println("2. Все строчные");
                        if (!scanner.hasNextLine()) {
                            return;
                        }
                        String register = scanner.nextLine().trim();
                        if (register.equals("1")) {
                            text = text.toUpperCase(Locale.ROOT);
                            System.out.println(text);
                        } else if (register.equals("2")) {
                            text = text.toLowerCase(Locale.ROOT);
                            System.out.println(text);
                        } else {
                            System.out.println("Неизвестный вариант регистра.");
                        }
                        break;
                    case "3":
                        System.out.println("Количество символов: " + text.length());
                        break;
                    case "0":
                        System.out.println("Программа завершена.");
                        return;
                    default:
                        System.out.println("Неизвестный пункт меню.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Удалить пробелы");
        System.out.println("2. Сделать все заглавными или строчными");
        System.out.println("3. Посчитать количество символов");
        System.out.println("0. Выход из программы");
    }
}
