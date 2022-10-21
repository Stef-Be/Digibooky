package com.switchfully.duckbusters.digibooky.service;


import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanService {

    private final LoanRepository loanRepo;

    public LoanService(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    public void LoanBook(LoanDTO loan){

    }
}
