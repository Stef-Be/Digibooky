package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.Person;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoanRepository {

    private final Map<String, BookLoan> loanMap = new HashMap<>();


    public void addLoan(BookLoan loan){
        loanMap.put(loan.getId(), loan);
    }

    public Collection<BookLoan> getAllLoans(){
        return loanMap.values();
    }
}
