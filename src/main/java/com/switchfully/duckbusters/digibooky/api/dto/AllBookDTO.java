package com.switchfully.duckbusters.digibooky.api.dto;

import com.switchfully.duckbusters.digibooky.domain.book.Author;

public class AllBookDTO {
    private  String isbn;
    private  String title;
    private  String author;

    public AllBookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public AllBookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public AllBookDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}
