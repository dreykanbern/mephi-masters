import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите число игроков от 1 до 10:");
            while (scanner.hasNextLine()) {
                try {
                    int players = Integer.parseInt(scanner.nextLine().trim());
                    if (players < 1 || players > 10) {
                        System.out.println("Число игроков должно быть от 1 до 10.");
                    } else {
                        dealCards(players);
                        return;
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Введите целое число от 1 до 10.");
                }
            }
        }
    }

    private static void dealCards(int players) {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"♣", "♦", "♥", "♠"};
        String[] deck = new String[ranks.length * suits.length];
        int index = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                deck[index] = rank + suit;
                index++;
            }
        }

        for (int player = 0; player < players; player++) {
            System.out.printf("Игрок %d:%n", player + 1);
            for (int card = 0; card < 5; card++) {
                System.out.println(deck[player * 5 + card]);
            }
            if (player < players - 1) {
                System.out.println();
            }
        }
    }
}
