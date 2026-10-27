import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char symbol = scanner.next().charAt(0);

        switch (symbol) {
            case 'h':
                System.out.println("Hello!");
                break;
            case 'i':
            case 'm':
            case 'k':
                System.out.println("I can decode!");
                break;
            case 'b':
                System.out.println("Bye!");
                break;
            default:
                System.out.println("I don't know these symbols :(");
        }
    }
}
