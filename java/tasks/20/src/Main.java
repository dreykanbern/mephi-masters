import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age = sc.nextInt();

        if (age < 14) {
            System.out.println("прокол ушей в присутствии родителей");
        } else if (age < 18) {
            System.out.println("прокол ушей, пупка, языка, носа в присутствии родителей");
        } else {
            System.out.println("без ограничений");
        }
    }
}
