package com.switchfully.duckbusters.digibooky.api.dto;

import java.time.LocalDate;

public class LoanReceipt {

    private final String loanId;

    private final String isbn;

    private final LocalDate dueDate;

    public LoanReceipt(String id, String isbn, LocalDate dueDate) {
        this.loanId = id;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
