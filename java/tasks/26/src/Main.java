public class Main {
    public static void main(String[] args) {
        for (int row = 1; row <= 9; row++) {
            System.out.print(row);
            for (int column = 1; column <= 9; column++) {
                System.out.print(" " + row * column);
            }
            System.out.println();
        }
    }
}
