public class Main {
    public static String string = "helloworld";

    public static void main(String[] args) {
        while (string.length() > 2) {
            System.out.println(string);
            string = string.substring(1, string.length() - 1);
        }
        System.out.println(string);
    }
}
