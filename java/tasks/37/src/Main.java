import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String[] ITEMS = new String[12];
    private static int size;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Команды: Проверить, Добавить, Удалить, Показать, Выход (0).");
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                switch (command) {
                    case "0", "выход":
                        return;
                    case "показать":
                        printItems();
                        break;
                    case "проверить", "добавить", "удалить":
                        System.out.println("Введите название предмета:");
                        if (!scanner.hasNextLine()) {
                            return;
                        }
                        String item = scanner.nextLine().trim();
                        if (item.isEmpty()) {
                            System.out.println("Название предмета не должно быть пустым.");
                        } else if (command.equals("проверить")) {
                            System.out.println(findIndex(item) >= 0 ? "Есть" : "Нет");
                        } else if (command.equals("добавить")) {
                            addItem(item);
                        } else {
                            removeItem(item);
                        }
                        break;
                    default:
                        System.out.println("Неизвестная команда.");
                }
            }
        }
    }

    private static int findIndex(String item) {
        for (int index = 0; index < size; index++) {
            if (ITEMS[index].equals(item)) {
                return index;
            }
        }
        return -1;
    }

    private static void addItem(String item) {
        if (findIndex(item) >= 0) {
            System.out.println("Предмет уже есть");
        } else if (size == ITEMS.length) {
            System.out.println("Невозможно добавить предмет. Место на складе кончилось");
        } else {
            ITEMS[size] = item;
            size++;
            System.out.println("Успешно добавлено");
        }
    }

    private static void removeItem(String item) {
        int index = findIndex(item);
        if (index < 0) {
            System.out.println("Удаление предмета невозможно. Предмета нет на складе");
            return;
        }
        for (int current = index; current < size - 1; current++) {
            ITEMS[current] = ITEMS[current + 1];
        }
        size--;
        ITEMS[size] = null;
        System.out.println("Успешно удалено");
    }

    private static void printItems() {
        if (size == 0) {
            System.out.println("Склад пуст.");
            return;
        }
        for (int index = 0; index < size; index++) {
            System.out.println(ITEMS[index]);
        }
    }
}
