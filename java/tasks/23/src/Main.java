public class Main {
    public static void main(String[] args) {
        int firstBucket = 30;
        int secondBucket = 0;
        for (int transfer = 1; transfer <= 30; transfer++) {
            firstBucket--;
            if (transfer % 10 != 0) {
                secondBucket++;
            }
            System.out.println(firstBucket);
            System.out.println(secondBucket);
        }
    }
}
