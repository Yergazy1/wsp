package classes;

import java.util.Date;

public class BorrowedBook {
    private Book book;
    private Date date;

    public BorrowedBook(Book book, Date date) {
        this.book = book;
        this.date = date;
    }
    public Book getBook() {
        return book;
    }
    public Date getDate() {
        return date;
    }
}