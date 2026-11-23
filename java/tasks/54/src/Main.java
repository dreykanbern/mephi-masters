import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern WHITESPACE = Pattern.compile("(?U)\\s+");

    public static void main(String[] args) throws IOException {
        System.out.println(arePalindromes());
    }

    public static boolean arePalindromes() throws IOException {
        for (String line : Files.readAllLines(Path.of("strings.txt"))) {
            String text = WHITESPACE.matcher(line).replaceAll("");
            String reversed = new StringBuilder(text).reverse().toString();
            if (!text.equalsIgnoreCase(reversed)) {
                return false;
            }
        }
        return true;
    }
}
