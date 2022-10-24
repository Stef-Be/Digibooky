package com.switchfully.duckbusters.digibooky.api.dto;

import java.time.LocalDate;

public class returnBookDTO {

    private String loanId;
    private LocalDate dueDate;
    private LocalDate returnDate = LocalDate.now();
    private String message;

    public returnBookDTO(String loanId, LocalDate dueDate, String message) {
        this.loanId = loanId;
        this.dueDate = dueDate;
        this.message = message;
    }

    public String getLoanId() {
        return loanId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getMessage() {
        return message;
    }
}
