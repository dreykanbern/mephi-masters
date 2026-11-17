import java.util.Arrays;

public class Main {
    public static int[] numbers = new int[100];

    public static void main(String[] args) {
        for (int index = 0; index < numbers.length; index++) {
            int value = index + 1;
            numbers[index] = value % 2 == 0 ? -value : value;
        }

        System.out.println(Arrays.toString(numbers));
    }
}
