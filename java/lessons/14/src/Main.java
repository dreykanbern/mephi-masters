public class Main {
    public static void main(String[] args) {
        String userName = "Вован Иванов";
        String existingUserName = "иван иванов";
        checkUserName(userName, existingUserName);
    }

    private static void checkUserName(String userName, String existingUserName) {
        if (userName.equalsIgnoreCase(existingUserName)) {
            System.out.println("Выберите другое имя пользователя");
        } else {
            System.out.println("Отличное имя!");
            System.out.println("Ваше имя имеет длину " + userName.length() + " символов");
        }
    }
}
