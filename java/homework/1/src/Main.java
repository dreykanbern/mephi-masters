import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        double result = 0;
        boolean needFirstOperand = true;
        String operation = "";

        System.out.println("Калькулятор: +, -, *, /.");
        System.out.println("C - сброс, S - выход. Команды работают при любом вводе.");
        System.out.println("Дробные числа вводите через точку.");

        while (true) {
            if (needFirstOperand) {
                System.out.print("Введите первое число: ");
            } else if (operation.isEmpty()) {
                System.out.print("Введите операцию: ");
            } else {
                System.out.print("Введите второе число: ");
            }

            if (!scanner.hasNext()) {
                break;
            }

            if (needFirstOperand && scanner.hasNextDouble()) {
                result = scanner.nextDouble();
                needFirstOperand = false;
                continue;
            }

            if (!operation.isEmpty() && scanner.hasNextDouble()) {
                double secondOperand = scanner.nextDouble();

                switch (operation) {
                    case "+":
                        result += secondOperand;
                        break;
                    case "-":
                        result -= secondOperand;
                        break;
                    case "*":
                        result *= secondOperand;
                        break;
                    case "/":
                        if (secondOperand == 0) {
                            System.out.println("Ошибка: делить на ноль нельзя.");
                        } else {
                            result /= secondOperand;
                        }
                        break;
                }

                System.out.println("Результат: " + result);
                operation = "";
                continue;
            }

            String input = scanner.next();

            if (input.equalsIgnoreCase("s")) {
                break;
            }

            if (input.equalsIgnoreCase("c")) {
                result = 0;
                needFirstOperand = true;
                operation = "";
                System.out.println("Результат сброшен: " + result);
                continue;
            }

            if (needFirstOperand || !operation.isEmpty()) {
                System.out.println("Ошибка: нужно ввести число.");
                continue;
            }

            if (input.equals("+") || input.equals("-")
                    || input.equals("*") || input.equals("/")) {
                operation = input;
            } else {
                System.out.println("Ошибка: неподдерживаемая операция.");
            }
        }

        scanner.close();
        System.out.println("Калькулятор выключен.");
    }
}
