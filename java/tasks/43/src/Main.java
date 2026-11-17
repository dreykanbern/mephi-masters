import java.util.Arrays;

public class Main {
    public static int[] numbers = new int[30];

    public static void main(String[] args) {
        for (int index = 0; index < numbers.length; index++) {
            if (index < 2) {
                numbers[index] = 1;
            } else {
                numbers[index] = numbers[index - 1] + numbers[index - 2];
            }
        }
        System.out.println(Arrays.toString(numbers));
    }
}
