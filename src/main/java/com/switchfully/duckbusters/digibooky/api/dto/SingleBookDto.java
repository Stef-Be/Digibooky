package com.switchfully.duckbusters.digibooky.api.dto;

import com.switchfully.duckbusters.digibooky.domain.book.Author;

import java.util.Optional;

public class SingleBookDto {
    private String isbn;
    private String title;
    private String author;
    private String summary;

    private String lender;

    public SingleBookDto setLender(String lender){
        this.lender = lender;
        return this;
    }

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
        this.author = author.getAuthorFirstname()+" "+author.getAuthorLastname();
        return this;
    }


    public String getLender() {
        return lender;
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

    public String getSummary() {
        return summary;
    }

}
