package com.neeko.comprehensive.persistence;

import com.neeko.comprehensive.domain.Book;

import java.util.List;

public interface BookStorage {
    void saveBook(List<Book> books);
    List<Book> loadBooks();
}
