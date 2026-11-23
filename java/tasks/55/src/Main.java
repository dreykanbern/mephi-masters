import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        solution();
    }

    public static void solution() throws IOException {
        List<String> input = Files.readAllLines(Path.of("data.txt"));
        int tolerance = Integer.parseInt(input.get(0).strip());
        List<String> lines = input.subList(1, input.size());

        long totalLength = lines.stream().mapToLong(String::length).sum();
        long averageLength = lines.isEmpty() ? 0 : totalLength / lines.size();
        List<String> output = new ArrayList<>();
        output.add(Long.toString(averageLength));
        for (String line : lines) {
            if (Math.abs(line.length() - averageLength) <= tolerance) {
                output.add(line);
            }
        }
        Files.write(Path.of("output.txt"), output);
    }
}
