public class Main {
    public static String text = "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available. It is also used to temporarily replace text in a process called greeking, which allows designers to consider the form of a webpage or publication, without the meaning of the text influencing the design.";

    public static String encrypt(String text, int shift) {
        int normalizedShift = Math.floorMod(shift, 26);
        StringBuilder result = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            char base;
            if (character >= 'A' && character <= 'Z') {
                base = 'A';
            } else if (character >= 'a' && character <= 'z') {
                base = 'a';
            } else {
                result.append(character);
                continue;
            }
            result.append((char) (base + (character - base + normalizedShift) % 26));
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, -Math.floorMod(shift, 26));
    }

    public static void main(String[] args) {
        System.out.println(encrypt(text, 5));
    }
}
