import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        double weight = scanner.nextDouble();
        double height = scanner.nextDouble();

        if (weight <= 0 || height <= 0) {
            System.out.println("Ошибка: вес и рост должны быть положительными числами.");
            return;
        }

        double bmi = Math.round(weight / (height * height) * 10.0) / 10.0;
        String message;
        if (bmi < 18.5) {
            message = "Ваш индекс массы тела ниже нормы. Возможно, стоит обратить внимание на питание.";
        } else if (bmi <= 24.9) {
            message = "Ваш индекс массы тела в норме! Продолжайте в том же духе.";
        } else if (bmi <= 29.9) {
            message = "Ваш индекс массы тела немного выше нормы. Рассмотрите возможность сбалансированного питания и физической активности.";
        } else {
            message = "Ваш индекс массы тела значительно выше нормы. Рекомендуем проконсультироваться с врачом или специалистом по питанию для получения рекомендаций.";
        }

        System.out.printf(Locale.US, "ИМТ: %.1f — %s%n", bmi, message);
    }
}
