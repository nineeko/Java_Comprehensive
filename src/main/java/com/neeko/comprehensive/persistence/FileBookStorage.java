package com.neeko.comprehensive.persistence;

import com.neeko.comprehensive.domain.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBookStorage implements BookStorage {
    private static final String FILE_PATH = "src/main/java/com/neeko/comprehensive/db/BookDB.dat";
    @Override
    public void saveBook(List<Book> books) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(books);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생",e);
        }
    }

    @Override
    public List<Book> loadBooks() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Book>) ois.readObject();
        } catch (EOFException e) {
            System.out.println("책 정보를 모두 로딩하였습니다.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("파일 로딩 중 오류 발생", e);
        }
    }
}
