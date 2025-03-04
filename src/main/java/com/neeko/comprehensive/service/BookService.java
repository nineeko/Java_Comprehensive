package com.neeko.comprehensive.service;
import com.neeko.comprehensive.persistence.BookRepository;
import com.neeko.comprehensive.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.selectAllBooks();
    }
    // 제목
    public List<Book> findBookByTitle(String title) {
        return bookRepository.selectBooksByTitle(title);
    }
    public Book findBookByTitleAndAuthor(String title, String author) {
        return bookRepository.selectBookByTitleAndAuthor(title,author);
    }
    // 작가
    public List<Book> findBookByAuthor(String author) {
        return bookRepository.selectBooksByAuthor(author);
    }

    // 출판사
    public List<Book> findBookByPublisher(String publisher) {
        return bookRepository.selectBooksByPublisher(publisher);
    }
    // 페이지
    public List<Book> findBookByPage(int page) {
        return bookRepository.selectBooksByPagesLessThan(page);
    }
    // 가격
    public List<Book> findBookByPrice(int maxPrice) {
        return bookRepository.selectBooksByPriceLessThan(maxPrice);
    }
    public void registerBook(Book book) {
        if (isDuplicateBook(book.getTitle(),book.getAuthor())) {
            throw new IllegalArgumentException("등록 실패 : 이미 등록 되어있는 책입니다.");
        }
        if(book.getPages()==0) {
            throw new IllegalArgumentException("등록 실패 : 책의 페이지 수는 0일 수 없습니다.");

        }
        bookRepository.insertBook(book);
    }
    private boolean isDuplicateBook(String bookTitle, String author) {
        return bookRepository.selectAllBooks()
                .stream()
                .anyMatch(book -> book.getTitle().equals(bookTitle) && book.getAuthor().equals(author));
    }
    public void removeBook(String title, String author) {

        Book existingBook = bookRepository.selectAllBooks().stream()
                .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                .findFirst()
                .orElse(null);

        if(existingBook == null) {
            throw new IllegalArgumentException("삭제 실패: 해당 책 제목과 작가를 가진 책을 찾을 수 없습니다.");
        }


        bookRepository.deleteBook(title);
    }

    public void modifyBook(Book updatedBook, String title, String author) {

        Book existingBook = bookRepository.selectAllBooks().stream()
                .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                .findFirst()
                .orElse(null);

        if(existingBook == null) {
            throw new IllegalArgumentException("책 정보 수정 실패: 해당 책 제목과 작가를 가진 책을 찾을 수 없습니다.");
        }


        bookRepository.updateBook(title, updatedBook);
    }


}