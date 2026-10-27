import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int variable = scanner.nextInt();

        variable = variable > 10 ? variable - 10 : variable + 10;
        System.out.println(variable);
    }
}
