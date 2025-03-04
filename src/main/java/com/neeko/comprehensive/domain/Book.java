package com.neeko.comprehensive.domain;

import java.io.Serializable;

public class Book implements Serializable{
    private String title;
    private String author;
    private String publisher;
    private int pages;
    private int price;

    public Book(String title, String author, String publisher, int pages, int price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", price=" + price +
                '}';
    }
}
