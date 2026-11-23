import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(findMaxProfit());
    }

    public static long findMaxProfit() throws IOException {
        String input = Files.readString(Path.of("trees.txt")).strip();
        if (input.isEmpty()) {
            return 0;
        }
        int[] trees = Arrays.stream(input.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (trees.length == 1) {
            return trees[0];
        }

        long withoutLast = maxProfitInRange(trees, 0, trees.length - 1);
        long withoutFirst = maxProfitInRange(trees, 1, trees.length);
        return Math.max(withoutLast, withoutFirst);
    }

    private static long maxProfitInRange(int[] trees, int start, int end) {
        long bestThroughPrevious = 0;
        long bestThroughTwoBefore = 0;
        for (int index = start; index < end; index++) {
            long bestThroughCurrent = Math.max(bestThroughPrevious, bestThroughTwoBefore + trees[index]);
            bestThroughTwoBefore = bestThroughPrevious;
            bestThroughPrevious = bestThroughCurrent;
        }
        return bestThroughPrevious;
    }
}
