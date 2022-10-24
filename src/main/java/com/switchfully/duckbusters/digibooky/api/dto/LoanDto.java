package com.switchfully.duckbusters.digibooky.api.dto;

import com.switchfully.duckbusters.digibooky.domain.loan.LoanStatus;

import java.time.LocalDate;

public class LoanDto {

    private String id;
    private String lender;

    private String book;
    private LocalDate dueDate;
    private LoanStatus status;

    public LoanDto(String id, String lender, String book, LocalDate dueDate, LoanStatus status) {
        this.id = id;
        this.lender = lender;
        this.book = book;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getLender() {
        return lender;
    }

    public String getBook() {
        return book;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanStatus getStatus() {
        return status;
    }
}
