import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        solution(80);
    }

    public static void solution(double n) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("EGE.txt"));
        for (int index = 1; index < lines.size(); index++) {
            String[] fields = lines.get(index).split(";");
            double passed = Double.parseDouble(fields[2].strip());
            if (passed >= n) {
                System.out.println(fields[0]);
            }
        }
    }
}
