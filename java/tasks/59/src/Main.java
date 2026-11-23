import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(findSquare());
    }

    public static double findSquare() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("points.txt"));
        double area = 0;
        long previousX = 0;
        long previousY = 0;
        for (int index = 0; index < lines.size(); index++) {
            String[] coordinates = lines.get(index).strip().split("\\s+");
            long x = Long.parseLong(coordinates[0]);
            long y = Long.parseLong(coordinates[1]);
            if (index > 0) {
                double averageY = (y + (double) previousY) / 2;
                area += averageY * (x - previousX);
            }
            previousX = x;
            previousY = y;
        }
        return area;
    }
}
