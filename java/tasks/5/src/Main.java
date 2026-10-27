import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите оператор: ");
        char operator = scanner.next().charAt(0);

        switch (operator) {
            case '+':
                System.out.println("Считан плюс");
                break;
            case '=':
                System.out.println("Считано равно");
                break;
        }
    }
}
