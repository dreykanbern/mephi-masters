package modul3;

public class Main {
    public static void main(String[] args) {
        printArrayStatistics();
    }

    private static void printArrayStatistics() {
        double[] numbers = {1.3, 3.2, 5.1, 4.7};
        for (double number : numbers) {
            System.out.println(number);
        }

        double sum = 0;
        double maximum = numbers[0];
        for (double number : numbers) {
            sum += number;
            if (number > maximum) {
                maximum = number;
            }
        }
        System.out.println("Сумма чисел массива: " + sum);
        System.out.println("Наибольший элемент: " + maximum);
    }
}
