package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Author;

public class SingleBookDto {
    private String isbn;
    private String title;
    private Author author;
    private String summary;

    public SingleBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }


    public SingleBookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public SingleBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public SingleBookDto setAuthor(Author author) {
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

    public String getSummary() {
        return summary;
    }

}
