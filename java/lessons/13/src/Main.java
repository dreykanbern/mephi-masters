public class Main {
    public static void main(String[] args) {
        String userName = "Иван Иванов";
        String existingUserName = "иван иванов";
        checkUserName(userName, existingUserName);
    }

    private static void checkUserName(String userName, String existingUserName) {
        if (userName.equalsIgnoreCase(existingUserName)) {
            System.out.println("Выберите другое имя пользователя");
        } else {
            System.out.println("Отличное имя!");
        }
    }
}
