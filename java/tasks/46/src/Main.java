public class Main {
    public static short size = 9;
    public static String[][] strings;

    public static void main(String[] args) {
        strings = new String[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                strings[row][column] = columnName(column) + (size - row);
            }
        }

        for (int i = 0; i < size; i++) {
            System.out.println(String.join(" ", strings[i]));
        }
    }

    private static String columnName(int column) {
        StringBuilder name = new StringBuilder();
        for (int value = column + 1; value > 0; value = (value - 1) / 26) {
            name.append((char) ('A' + (value - 1) % 26));
        }
        return name.reverse().toString();
    }
}
