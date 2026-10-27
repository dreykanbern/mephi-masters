public class Bogatyr {
    private static final int WEAPON_DAMAGE = 100;
    private final String name = "Добрыня";
    private final String heroClass = "Богатырь";
    private final String weapon = "Меч - Кладенец (Урон: 100)";

    public static void main(String[] args) {
        Bogatyr bogatyr = new Bogatyr();
        bogatyr.fightGorynych();
    }

    public void fightGorynych() {
        exitFromKiev();
        goToKudykinaGora();
        saddleHorse();
        searchGorynych();
        System.out.println("Одна голова хорошо, а три - уже Горыныч.");

        String[] heads = {"Первая", "Вторая", "Третья"};
        int damage = 0;
        for (int hit = 0; hit < heads.length; hit++) {
            damage += WEAPON_DAMAGE;
            System.out.println(heads[hit] + " голова отрублена");
            System.out.println("Суммарный урон: " + damage);
        }
        System.out.println("Горыныч побежден.");
    }

    private void exitFromKiev() {
        System.out.println("Выходим из Киева...");
    }

    private void goToKudykinaGora() {
        System.out.println("Идем на Кудыкину гору...");
    }

    private void saddleHorse() {
        System.out.println("Садимся на коня...");
    }

    private void searchGorynych() {
        System.out.println("Где эта змеюка...?");
    }
}
