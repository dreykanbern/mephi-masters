public class Main {
    public static void main(String[] args) {
        for (int row = 0; row <= 10; row++) {
            int lastNumber = row <= 5 ? row : 10 - row;
            for (int number = 0; number <= lastNumber; number++) {
                if (number > 0) {
                    System.out.print("  ");
                }
                System.out.print(number);
            }
            System.out.println();
        }
    }
}
