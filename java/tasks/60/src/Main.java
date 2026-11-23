import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        solve();
    }

    public static void solve() throws IOException {
        List<String> answers = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of("arithmetic.txt"))) {
            String expression = line.strip();
            String[] parts = expression.split("\\s+");
            BigInteger left = new BigInteger(parts[0]);
            String operator = parts[1];
            BigInteger right = new BigInteger(parts[2]);
            BigInteger result = switch (operator) {
                case "+" -> left.add(right);
                case "-" -> left.subtract(right);
                case "*" -> left.multiply(right);
                default -> throw new IllegalArgumentException("Неизвестный оператор " + operator);
            };
            answers.add(expression + " = " + result);
        }
        Files.write(Path.of("answers.txt"), answers);
    }
}
