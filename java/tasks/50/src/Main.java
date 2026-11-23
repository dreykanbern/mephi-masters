import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(getReceipt());
    }

    public static String getReceipt() throws IOException {
        BigDecimal total = BigDecimal.ZERO;
        for (String line : Files.readAllLines(Path.of("receipt.txt"))) {
            String[] fields = line.split(";");
            BigDecimal quantity = new BigDecimal(fields[1].strip());
            BigDecimal price = new BigDecimal(fields[2].strip());
            total = total.add(quantity.multiply(price));
        }
        return total.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
