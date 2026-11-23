import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print(translate());
    }

    public static String translate() throws IOException {
        String message = Files.readString(Path.of("message.txt"));
        StringBuilder result = new StringBuilder(message.length());
        for (int index = 0; index < message.length(); index++) {
            char character = message.charAt(index);
            if (character >= 'A' && character <= 'Z') {
                result.append(Integer.toBinaryString(character));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}
