package com.switchfully.duckbusters.digibooky.api.dto;

import java.time.LocalDate;

public class LoanReceipt {

    private final String id;

    private final String isbn;

    private final LocalDate dueDate;

    public LoanReceipt(String id, String isbn, LocalDate dueDate) {
        this.id = id;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
