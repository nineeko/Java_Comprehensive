package com.neeko.comprehensive.ui;
import com.neeko.comprehensive.domain.Book;
import com.neeko.comprehensive.persistence.BookRepository;
import com.neeko.comprehensive.persistence.FileBookStorage;
import com.neeko.comprehensive.service.BookService;

import java.util.List;
import java.util.Scanner;

public class Application {
    private final BookService bookService;
    private final Scanner scanner;

    public Application(){
        BookRepository bookRepository = new BookRepository(new FileBookStorage());
        this.bookService = new BookService(bookRepository);
        this.scanner = new Scanner(System.in);
    }
    public void run() {
        while (true) {
            System.out.println("\n===== 도서 관리 프로그램 =====");
            System.out.println("1. 모든 도서 정보 조회");
            System.out.println("2. 도서 찾기");
            System.out.println("3. 도서 등록");
            System.out.println("4. 도서 정보 수정");
            System.out.println("5. 도서 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1 -> showAllBooks();
                    case 2 -> findBookBySomething();
                    case 3 -> registerBook();
                    case 4 -> modifyBook();
                    case 5 -> removeBook();
                    case 9 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }


    private void showAllBooks() {
            bookService.findAllBooks().forEach(System.out::println);
        }

    private void findBookBySomething() {
        while (true) {
            System.out.println("1. 책 제목으로 조회");
            System.out.println("2. 작가 이름으로 조회");
            System.out.println("3. 페이지 수로 조회");
            System.out.println("4. 가격으로 조회");
            System.out.println("5. 출판사로 조회");
            System.out.println("9. 조회 종료");
            System.out.print("조회 방법 선택: ");

            int choice1 = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice1) {
                    case 1 -> findBookByTitle();
                    case 2 -> findBookByAuthor();
                    case 3 -> findBookByPages();
                    case 4 -> findBookByPrice();
                    case 5 -> findBookByPublisher();
                    case 9 -> {
                        System.out.println("조회를 종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }



    }


    private void findBookByTitle() {
        System.out.print("조회할 책 제목 입력: ");
        String title = scanner.nextLine();

        List<Book> books = bookService.findBookByTitle(title);

        if (!books.isEmpty()) {
            books.forEach(book -> System.out.println("책 제목: " + book.getTitle() + ", 작가: " + book.getAuthor()));
        } else {
            System.out.println("해당 책 제목을 가진 책을 찾을 수 없습니다.");
        }
    }
    private void findBookByAuthor() {
        System.out.print("조회할 작가 이름 입력: ");
        String author = scanner.nextLine();

        List<Book> books = bookService.findBookByAuthor(author);

        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("해당 작가의 책을 찾을 수 없습니다.");
        }
    }
    private void findBookByPages() {
        System.out.print("최대 페이지 수 입력: ");
        int maxPages = scanner.nextInt();
        scanner.nextLine();

        List<Book> books = bookService.findBookByPage(maxPages);

        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println(maxPages + "페이지 이하의 책을 찾을 수 없습니다.");
        }
    }
    private void findBookByPrice() {
        System.out.print("최대 가격 입력: ");
        int maxPrice = scanner.nextInt();
        scanner.nextLine();

        List<Book> books = bookService.findBookByPrice(maxPrice);

        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println(maxPrice + "원 이하의 책을 찾을 수 없습니다.");
        }
    }
    private void findBookByPublisher() {
        System.out.print("조회할 출판사 입력: ");
        String publisher = scanner.nextLine();

        List<Book> books = bookService.findBookByPublisher(publisher);

        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("해당 출판사의 책을 찾을 수 없습니다.");
        }
    }


    private void registerBook() {
        try {
            System.out.print("책 제목 입력: ");
            String title = scanner.nextLine();

            System.out.print("작가명 입력: ");
            String author = scanner.nextLine();

            System.out.print("출판사 입력: ");
            String publisher = scanner.nextLine();

            System.out.print("페이지수 입력: ");
            int pages  = scanner.nextInt();
            scanner.nextLine();

            System.out.print("가격 입력: ");
            int price = scanner.nextInt();
            scanner.nextLine();

            Book newBook = new Book(title, author, publisher, pages, price);

            bookService.registerBook(newBook);
            System.out.println("책 등록 성공: " + title);

        } catch (IllegalArgumentException e) {
            System.out.println("책 등록 실패: " + e.getMessage());
        }
    }

    private void modifyBook() {
        try {

            System.out.print("수정할 책의 제목 입력: ");
            String title = scanner.nextLine();

            System.out.print("수정할 책의 작가 이름 입력: ");
            String author = scanner.nextLine();


            Book existingBooks = bookService.findBookByTitleAndAuthor(title, author);
            if (existingBooks == null) {
                System.out.println("해당 제목과 작가를 가진 책을 찾을 수 없습니다.");
                return;
            }



            System.out.println("새로운 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            System.out.print("새로운 제목 (" + existingBooks.getTitle() + "): ");
            String title1 = scanner.nextLine();
            if (title1.isEmpty()) title1 = existingBooks.getTitle();

            System.out.println("새로운 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            System.out.print("새로운 작가명 (" + existingBooks.getAuthor() + "): ");
            String author1 = scanner.nextLine();
            if (author1.isEmpty()) author1 = existingBooks.getAuthor();

            System.out.println("새로운 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            System.out.print("새로운 출판사 (" + existingBooks.getPublisher() + "): ");
            String publisher1 = scanner.nextLine();
            if (publisher1.isEmpty()) publisher1 = existingBooks.getPublisher();

            System.out.println("새로운 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            System.out.print("새로운 페이지 수  (" + existingBooks.getPages() + "): ");
            String pagesInput = scanner.nextLine();
            int pages = pagesInput.isEmpty() ? existingBooks.getPages() : Integer.parseInt(pagesInput);

            System.out.println("새로운 정보를 입력하세요 (변경하지 않으려면 Enter 입력)");
            System.out.print("새로운 가격  (" + existingBooks.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            int price = pagesInput.isEmpty() ? existingBooks.getPrice() : Integer.parseInt(priceInput);


            Book updatedBook = new Book(title1, author1, publisher1, pages, price);
            bookService.modifyBook(updatedBook,title,author);
            System.out.println("책 정보 수정 완료: " + title1+" "+author1);

        } catch (IllegalArgumentException e) {
            System.out.println("책 정보 수정 실패: " + e.getMessage());
        }
    }
    private void removeBook() {
        try {

            System.out.print("삭제할 책의 제목 입력: ");
            String title = scanner.nextLine();

            System.out.print("삭제할 책의 작가 이름 입력: ");
            String author = scanner.nextLine();


            bookService.removeBook(title,author);
            System.out.println("책 삭제 완료 (책 제목 (작가 명): " + title +" ("+author+ ")");



        } catch (IllegalArgumentException e) {
            System.out.println("책 삭제 실패: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}

