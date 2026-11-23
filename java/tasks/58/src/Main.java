import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final BigDecimal GROWTH_FACTOR = new BigDecimal("1.04");

    public static void main(String[] args) throws IOException {
        solution("Северо-Западный", 2018, 2019);
    }

    public static void solution(String district, int fromYear, int toYear) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("crop_volumes.txt"));
        List<String> headers = Arrays.asList(lines.get(0).split(";"));
        int fromColumn = headers.indexOf(Integer.toString(fromYear));
        int toColumn = headers.indexOf(Integer.toString(toYear));

        List<String> output = new ArrayList<>();
        output.add(headers.get(0) + ";" + fromYear + ";" + toYear);
        if (fromColumn >= 0 && toColumn >= 0) {
            for (int index = 1; index < lines.size(); index++) {
                String[] fields = lines.get(index).split(";");
                if (!fields[1].equals(district)) {
                    continue;
                }

                BigDecimal fromVolume = new BigDecimal(fields[fromColumn].strip());
                BigDecimal toVolume = new BigDecimal(fields[toColumn].strip());
                if (toVolume.compareTo(fromVolume.multiply(GROWTH_FACTOR)) > 0) {
                    output.add(fields[0] + ";" + fields[fromColumn] + ";" + fields[toColumn]);
                }
            }
        }
        Files.write(Path.of("data.txt"), output);
    }
}
