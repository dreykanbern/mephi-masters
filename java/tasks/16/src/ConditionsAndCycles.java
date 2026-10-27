public class ConditionsAndCycles {
    public static void main(String[] args) {
        int a = 4, b = 3, c = 2;
        // b = b + c;
        a = a * b;
        c = a - c;
        a = a + b;
        a = a - b;
        c = a + b;
        // b = c / a;
        a = b + c;
        // a = a + c + b;

        for (int i = 0; i < 20; i++) {
            System.out.println(a);
        }
    }
}
