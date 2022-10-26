package com.switchfully.duckbusters.digibooky.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public SingleBookDto setAuthor(String author) {
        this.author = author;
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
