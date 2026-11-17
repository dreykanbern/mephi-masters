package bookstore;

public class Book {
    private final String title;
    private final int price;
    private final int publicationYear;

    public Book(String title, int price, int publicationYear) {
        this.title = title;
        this.price = price;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
