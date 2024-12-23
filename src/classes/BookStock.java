package classes;

public class BookStock {
    private Book book;
    private int quantity;

    public BookStock(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }
    public Book getBook() {
        return book;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}