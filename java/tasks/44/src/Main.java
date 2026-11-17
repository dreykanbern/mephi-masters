import java.util.Arrays;

public class Main {
    public static short size = 3;
    public static int[][] ints = new int[size][];

    public static void main(String[] args) {
        ints = new int[size][];
        for (int row = 0; row < size; row++) {
            ints[row] = new int[row + 1];
            ints[row][0] = 1;
            ints[row][row] = 1;
            for (int column = 1; column < row; column++) {
                ints[row][column] = ints[row - 1][column - 1] + ints[row - 1][column];
            }
        }

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(ints[i]));
        }
    }
}
