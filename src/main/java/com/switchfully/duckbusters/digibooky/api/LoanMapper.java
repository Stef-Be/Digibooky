package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.BookLoan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public BookLoan createNewLoan(AddLoanDTO loan){

        return new BookLoan(loan.getMemberId(), loan.getIsbn());
    }

}
