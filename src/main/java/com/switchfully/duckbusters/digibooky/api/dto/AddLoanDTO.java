package com.switchfully.duckbusters.digibooky.api.dto;

public class AddLoanDTO {

    private String memberId;
    private String isbn;

    public AddLoanDTO(String memberId, String isbn) {
        this.memberId = memberId;
        this.isbn = isbn;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getIsbn() {
        return isbn;
    }
}
