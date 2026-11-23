import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) throws IOException {
        writeOrder();
    }

    public static void writeOrder() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("numbers.txt"));
        StringJoiner matches = new StringJoiner(" ").setEmptyValue("0");
        for (int index = 0; index < lines.size(); index++) {
            int position = index + 1;
            int number = Integer.parseInt(lines.get(index).strip());
            if (number == position) {
                matches.add(Integer.toString(number));
            }
        }
        Files.writeString(Path.of("output.txt"), matches.toString());
    }
}
