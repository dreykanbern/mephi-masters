import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{Nd}]+(?:['’][\\p{L}\\p{Nd}]+)*+");

    public static void main(String[] args) throws IOException {
        System.out.println(getCountWords());
    }

    public static int getCountWords() throws IOException {
        String text = Files.readString(Path.of("text.txt"));
        Matcher words = WORD.matcher(text);
        int count = 0;
        while (words.find()) {
            count++;
        }
        return count;
    }
}
