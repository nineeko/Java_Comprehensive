package com.neeko.comprehensive.persistence;

import com.neeko.comprehensive.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {
    private final BookStorage bookStorage;
    private final List<Book> bookList;
    public BookRepository(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
        this.bookList = bookStorage.loadBooks();
    }
    public List<Book> selectAllBooks() {
        return new ArrayList<>(bookList);
    }
    public List<Book> selectBooksByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList()); // 여러 책을 반환
    }
    public Book selectBookByTitleAndAuthor(String title, String author) {
        return bookList.stream()
                .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                .findFirst()  // 첫 번째 일치하는 책을 찾음
                .orElse(null); // 없으면 null 반환
    }


    public void insertBook(Book book) {
        bookList.add(book);
        bookStorage.saveBook(bookList);
    }
    public void deleteBook(String title) {
        bookList.removeIf(book->book.getTitle().equals(title));
        bookStorage.saveBook(bookList);
    }
    public void updateBook(String title, Book updatedBook) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)) {
                bookList.set(i, updatedBook);
                bookStorage.saveBook(bookList); // 저장소 업데이트
                return;
            }
        }

    }
    public List<Book> selectBooksByAuthor(String author) {
        return bookList.stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }
    public List<Book> selectBooksByPublisher(String publisher) {
        return bookList.stream()
                .filter(book -> book.getPublisher().equals(publisher))
                .collect(Collectors.toList());
    }
    public List<Book> selectBooksByPriceLessThan(int maxPrice) {
        return bookList.stream()
                .filter(book -> book.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    public List<Book> selectBooksByPagesLessThan(int maxPages) {
        return bookList.stream()
                .filter(book -> book.getPages() <= maxPages)
                .collect(Collectors.toList());
    }

}
