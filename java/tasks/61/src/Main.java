import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.15");

    public static void main(String[] args) throws IOException {
        findMerchant("Cherevik");
    }

    public static void findMerchant(String merchant) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("outcome.txt"));
        BigDecimal total = BigDecimal.ZERO;
        for (int index = 1; index < lines.size(); index++) {
            String[] fields = lines.get(index).split(";");
            if (!fields[1].equals(merchant)) {
                continue;
            }

            BigDecimal quantity = new BigDecimal(fields[3].strip());
            BigDecimal price = new BigDecimal(fields[4].strip());
            BigDecimal income = quantity.multiply(price);
            String amount = income.stripTrailingZeros().toPlainString();
            System.out.println(String.join(",", fields[2], fields[3], fields[4], amount));
            total = total.add(income);
        }

        BigDecimal tax = total.multiply(TAX_RATE);
        System.out.println("tax: " + tax.stripTrailingZeros().toPlainString());
    }
}
