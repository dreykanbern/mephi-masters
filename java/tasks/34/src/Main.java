import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] words = {"Java", "массивы", "строки", "методы", "циклы"};
        reverse(words);
        System.out.println(Arrays.toString(words));
    }

    private static void reverse(String[] words) {
        for (int left = 0, right = words.length - 1; left < right; left++, right--) {
            String temporary = words[left];
            words[left] = words[right];
            words[right] = temporary;
        }
    }
}
