import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (int number = 1; number <= 5; number++) {
            output.write(number);
        }

        byte[] bytes = output.toByteArray();
        System.out.println(Arrays.toString(bytes));
    }
}
