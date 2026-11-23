import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(findMaxQuantity());
    }

    public static int findMaxQuantity() throws IOException {
        List<String> rows = Files.readAllLines(Path.of("forest.txt"));
        int columns = rows.get(0).split(";").length;
        int[] best = new int[columns];
        for (String row : rows) {
            String[] counts = row.split(";");
            for (int column = 0; column < columns; column++) {
                int fromAbove = best[column];
                int fromLeft = column == 0 ? 0 : best[column - 1];
                int mushrooms = Integer.parseInt(counts[column].strip());
                best[column] = Math.max(fromAbove, fromLeft) + mushrooms;
            }
        }
        return best[columns - 1];
    }
}
