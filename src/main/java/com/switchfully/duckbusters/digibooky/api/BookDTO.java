package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Author;

public class BookDTO {
    private  String isbn;
    private  String title;
    private  Author author;

    public BookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookDTO setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

}
