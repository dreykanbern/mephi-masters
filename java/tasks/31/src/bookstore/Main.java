package bookstore;

public class Main {
    private static final Book[] BOOKS = new Book[3];

    public static void main(String[] args) {
        addBook(new Book("The Hobbit", 999, 1937));
        addBook(new Book("The Lord of the Rings", 1499, 1954));
        addBook(new Book("The Hobbit", 899, 1937));
        addBook(new Book("The Hobbit", 1099, 1966));
        addBook(new Book("Dune", 1299, 1965));
        printBooks();
    }

    private static Book findBook(String title, int publicationYear) {
        for (Book book : BOOKS) {
            if (book != null && book.getTitle().equals(title)
                    && book.getPublicationYear() == publicationYear) {
                return book;
            }
        }
        return null;
    }

    private static boolean addBook(Book book) {
        if (findBook(book.getTitle(), book.getPublicationYear()) != null) {
            System.out.printf("Такая книга уже есть в картотеке: \"%s\", %d г.%n",
                    book.getTitle(), book.getPublicationYear());
            return false;
        }
        for (int index = 0; index < BOOKS.length; index++) {
            if (BOOKS[index] == null) {
                BOOKS[index] = book;
                System.out.printf("Книга добавлена: \"%s\", %d г.%n",
                        book.getTitle(), book.getPublicationYear());
                return true;
            }
        }
        System.out.println("Картотека заполнена.");
        return false;
    }

    private static void printBooks() {
        for (int index = 0; index < BOOKS.length; index++) {
            Book book = BOOKS[index];
            if (book != null) {
                System.out.printf("книга №%d: название: \"%s\", год издания: %dг., цена - %dusd%n",
                        index + 1, book.getTitle(), book.getPublicationYear(), book.getPrice());
            }
        }
    }
}
