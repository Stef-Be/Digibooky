package com.switchfully.duckbusters.digibooky.domain.book;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
    private String summary;

    private boolean inCatalogue;

    public String getSummary() {
        return summary;
    }

    public Book(String isbn, String title, Author author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.inCatalogue = true;
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

    public boolean isInCatalogue() {
        return inCatalogue;
    }

    public void setInCatalogue(boolean inCatalogue) {
        this.inCatalogue = inCatalogue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
