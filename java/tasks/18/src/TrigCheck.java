import java.util.Locale;
import java.util.Scanner;

public class TrigCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        double h = sc.nextDouble();
        final double EPS = 1e-12;

        for (double x = 0; x <= 2 * Math.PI + EPS; x += h) {
            double left = Math.sin(Math.cos(x));
            double right = Math.cos(Math.sin(x));
            if (left <= right + EPS) {
                System.out.println("Утверждение неверно");
                System.out.printf(Locale.US, "x=%.6f%n", x);
                System.out.printf(Locale.US, "sin(cos(x))=%.12f%n", left);
                System.out.printf(Locale.US, "cos(sin(x))=%.12f%n", right);
                return;
            }
        }

        System.out.println("Утверждение верно");
    }
}
