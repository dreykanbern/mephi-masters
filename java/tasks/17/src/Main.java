import java.util.Locale;

public class Main {
    static final int TRIANGLE_SIDE = 3;
    static final int SQUARE_SIDE = 4;
    static final int CIRCLE_RADIUS = 5;

    public static void main(String[] args) {
        System.out.println("Perimeter triangle = " + PerimeterCalculator.triangle(TRIANGLE_SIDE));
        System.out.println();
        System.out.println("Perimeter square = " + PerimeterCalculator.square(SQUARE_SIDE));
        System.out.println();
        System.out.printf(Locale.US, "Perimeter circle = %.2f%n",
                PerimeterCalculator.circle(CIRCLE_RADIUS));
    }
}
