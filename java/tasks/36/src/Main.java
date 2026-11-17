import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] numbers = {27, 97, 86, 88, 31, 46, 59, 48, 16, 4};
        reverse(numbers);
        System.out.println("В обратном порядке: " + Arrays.toString(numbers));

        Arrays.sort(numbers);
        reverse(numbers);
        System.out.println("По убыванию: " + Arrays.toString(numbers));
    }

    private static void reverse(int[] numbers) {
        for (int left = 0, right = numbers.length - 1; left < right; left++, right--) {
            int temporary = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temporary;
        }
    }
}
