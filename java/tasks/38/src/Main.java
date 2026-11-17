import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] grades = {5, 2, 4, 3, 2, 5, 2};
        replaceTwosWithFives(grades);
        System.out.println(Arrays.toString(grades));
    }

    private static void replaceTwosWithFives(int[] grades) {
        for (int index = 0; index < grades.length; index++) {
            if (grades[index] == 2) {
                grades[index] = 5;
            }
        }
    }
}
