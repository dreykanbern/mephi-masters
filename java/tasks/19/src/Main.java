public class Main {
    public static void main(String[] args) {
        for (int number = 2; number <= 100; number++) {
            boolean prime = true;
            for (int divisor = 2; divisor * divisor <= number; divisor++) {
                if (number % divisor == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                System.out.println(number);
            }
        }
    }
}
