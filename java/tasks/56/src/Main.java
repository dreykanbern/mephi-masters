import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        dance();
    }

    public static void dance() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("dance.txt"));
        long rhythm = Long.parseLong(lines.get(0).strip());
        List<String> counts = new ArrayList<>();
        for (int index = 1; index < lines.size(); index++) {
            long steps = Long.parseLong(lines.get(index).strip());
            counts.add(steps / rhythm + " " + steps % rhythm);
        }
        Files.write(Path.of("rhythm.txt"), counts);
    }
}
