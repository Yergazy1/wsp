package classes;

import java.util.*;
import java.text.*;

public class Librarian extends Employee{
	private Map<String, BookStock> books = new HashMap<>();
	private Map<Student, List<BorrowedBook>> transactions = new HashMap<>();

	public Librarian(String id, String userName, String password) {
		super(id, userName, password, password);
	}

	public void addBook(Book book, int quantity) {
		BookStock stock = books.get(book.getIsbn());
		if (stock != null) {
			stock.setQuantity(stock.getQuantity() + quantity);
		} else {
			books.put(book.getIsbn(), new BookStock(book, quantity));
		}
	}

	public void borrowBook(Student student, Book book, int durationMonths) {
		if (durationMonths > 6) {
			System.out.println("Error: Maximum borrowing period is 6 months.");
			return;
		}

		BookStock stock = books.get(book.getIsbn());
		if (stock == null || stock.getQuantity() == 0) {
			System.out.println("The book '" + book.getTitle() + "' is not available.");
			return;
		}

		Calendar dueDate = Calendar.getInstance();
		dueDate.add(Calendar.MONTH, durationMonths);

		BorrowedBook borrowedBook = new BorrowedBook(book, dueDate.getTime());
		transactions.putIfAbsent(student, new ArrayList<>());
		transactions.get(student).add(borrowedBook);

		stock.setQuantity(stock.getQuantity() - 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(student.getName() + " successfully borrowed '" + book.getTitle() + "' until " + dateFormat.format(dueDate.getTime()));
	}

	public void returnBook(Student student, Book book) {
		List<BorrowedBook> borrowedBooks = transactions.get(student);
		if (borrowedBooks != null) {
			for (Iterator<BorrowedBook> iterator = borrowedBooks.iterator(); iterator.hasNext(); ) {
				BorrowedBook borrowedBook = iterator.next();
				if (borrowedBook.getBook().getIsbn().equals(book.getIsbn())) {
					iterator.remove();
					BookStock stock = books.get(book.getIsbn());
					if (stock != null) {
						stock.setQuantity(stock.getQuantity() + 1);
					}
					System.out.println(student.getName() + " successfully returned '" + book.getTitle() + "'");
					return;
				}
			}
		}
		System.out.println(student.getName() + " did not borrow '" + book.getTitle() + "'");
	}

	public void printLibraryStatus() {
		System.out.println("\nLibrary books after transactions:");
		for (BookStock stock : books.values()) {
			System.out.println(stock.getBook().getTitle() + " (" + stock.getBook().getIsbn() + "): " + stock.getQuantity() + " copies available");
		}
	}

	public Book getBookByIsbn(String isbn) {
		return books.containsKey(isbn) ? books.get(isbn).getBook() : null;
	}
}