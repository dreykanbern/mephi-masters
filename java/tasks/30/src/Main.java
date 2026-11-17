public class Main {
    public static void main(String[] args) {
        String[] names = {"Саша", "Игорь", "Миша", "Коля", "Владимир"};
        int[][] scores = {
                {8, 9, 7, 10, 6},
                {9, 8, 10, 7, 9},
                {6, 7, 8, 9, 10},
                {10, 9, 9, 8, 7},
                {7, 10, 8, 10, 9}
        };

        String[] results = summarizeScores(names, scores);
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static String[] summarizeScores(String[] names, int[][] scores) {
        String[] results = new String[names.length];
        for (int player = 0; player < names.length; player++) {
            int total = 0;
            for (int score : scores[player]) {
                total += score;
            }
            results[player] = names[player] + "  " + total;
        }
        return results;
    }
}
