import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long orcHealth = sc.nextInt();
        int orcDamage = sc.nextInt();
        long elfHealth = sc.nextInt();
        int elfDamage = sc.nextInt();

        while (true) {
            elfHealth -= orcDamage;
            orcHealth -= elfDamage;

            if (orcHealth <= 0 && elfHealth <= 0) {
                System.out.println("Draw!");
                break;
            } else if (elfHealth <= 0) {
                System.out.println("Orc wins!");
                break;
            } else if (orcHealth <= 0) {
                System.out.println("Elf wins!");
                break;
            }
        }
    }
}
