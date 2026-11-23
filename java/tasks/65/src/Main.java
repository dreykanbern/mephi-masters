import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final BigDecimal KILOBYTE = BigDecimal.valueOf(1024);
    private static final BigDecimal MEGABYTE = KILOBYTE.multiply(KILOBYTE);
    private static final Pattern FILE_LINE = Pattern.compile("(.+?)\\s+(\\S+)\\s+(\\S+)");

    private record FileEntry(String name, BigDecimal sizeInBytes) {}

    public static void main(String[] args) throws IOException {
        solution();
    }

    public static void solution() throws IOException {
        Map<String, List<FileEntry>> groups = new TreeMap<>();
        for (String line : Files.readAllLines(Path.of("files.txt"))) {
            Matcher fields = FILE_LINE.matcher(line.strip());
            if (!fields.matches()) {
                throw new IllegalArgumentException("Некорректное описание файла " + line);
            }

            String name = fields.group(1);
            String extension = name.substring(name.lastIndexOf('.') + 1).toUpperCase(Locale.ROOT);
            BigDecimal size = new BigDecimal(fields.group(2));
            String unit = fields.group(3);
            BigDecimal sizeInBytes = switch (unit) {
                case "B" -> size;
                case "KB" -> size.multiply(KILOBYTE);
                case "MB" -> size.multiply(MEGABYTE);
                default -> throw new IllegalArgumentException("Неизвестная единица измерения " + unit);
            };
            groups.computeIfAbsent(extension, key -> new ArrayList<>()).add(new FileEntry(name, sizeInBytes));
        }

        List<String> output = new ArrayList<>();
        for (List<FileEntry> files : groups.values()) {
            if (!output.isEmpty()) {
                output.add("");
            }
            files.sort(Comparator.comparing(FileEntry::name, String.CASE_INSENSITIVE_ORDER));
            BigDecimal total = BigDecimal.ZERO;
            for (FileEntry file : files) {
                output.add(file.name());
                total = total.add(file.sizeInBytes());
            }
            output.add("----------");
            output.add("Summary: " + formatSize(total));
        }
        Files.write(Path.of("output.txt"), output);
    }

    private static String formatSize(BigDecimal bytes) {
        if (bytes.compareTo(MEGABYTE) >= 0) {
            return bytes.divide(MEGABYTE, 0, RoundingMode.HALF_UP).toPlainString() + " MB";
        }
        if (bytes.compareTo(KILOBYTE) >= 0) {
            return bytes.divide(KILOBYTE, 0, RoundingMode.HALF_UP).toPlainString() + " KB";
        }
        return bytes.setScale(0, RoundingMode.HALF_UP).toPlainString() + " B";
    }
}
