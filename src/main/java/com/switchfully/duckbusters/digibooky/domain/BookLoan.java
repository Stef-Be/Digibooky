package com.switchfully.duckbusters.digibooky.domain;

import java.time.LocalDate;
import java.util.UUID;

import static com.switchfully.duckbusters.digibooky.domain.LoanStatus.*;

public class BookLoan {

    private final String member;
    private final String id;
    private LocalDate dueDate;
    private final String book;
    private LoanStatus status;

    public BookLoan(String member, String book) {
        this.id = UUID.randomUUID().toString();
        this.dueDate = LocalDate.now().plusDays(21);
        this.member = member;
        this.book = book;
        this.status = LOANED_OUT;
    }

    public String getId() {
        return id;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public String getBook() {
        return book;
    }
}
