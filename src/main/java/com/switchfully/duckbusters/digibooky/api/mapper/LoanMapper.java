package com.switchfully.duckbusters.digibooky.api.mapper;

import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDto mapLoanToDTO(BookLoan loan){
        return new LoanDto(loan.getId(), loan.getMember(), loan.getIsbn(),loan.getDueDate(),loan.getStatus());
    }

}
