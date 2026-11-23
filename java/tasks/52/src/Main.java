import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(getMul());
    }

    public static BigInteger getMul() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("data.txt"));
        int limit = Integer.parseInt(lines.get(0).strip());
        String divisorLine = lines.get(1).strip();

        BigInteger product = BigInteger.ONE;
        if (divisorLine.isEmpty()) {
            return product;
        }

        int[] divisors = Arrays.stream(divisorLine.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        for (long number = 1; number <= limit; number++) {
            for (int divisor : divisors) {
                if (divisor != 0 && number % divisor == 0) {
                    product = product.multiply(BigInteger.valueOf(number));
                    break;
                }
            }
        }
        return product;
    }
}
