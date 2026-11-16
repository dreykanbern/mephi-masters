import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Калькулятор: +, -, *, /.");
        System.out.println("Дробные числа вводите через точку.");

        System.out.print("Введите первое число: ");
        double result = scanner.nextDouble();
        System.out.print("Введите операцию: ");
        String operation = scanner.next();
        System.out.print("Введите второе число: ");
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
            default:
                System.out.println("Ошибка: неподдерживаемая операция.");
                break;
        }

        System.out.println("Результат: " + result);
        scanner.close();
    }
}
