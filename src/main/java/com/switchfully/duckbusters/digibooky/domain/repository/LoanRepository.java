package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.api.mapper.BookMapper;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.loan.LoanStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoanRepository {

    private PersonRepository personRepository;

    public LoanRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private final Map<String, BookLoan> loanMap = new HashMap<>();


    public void addLoan(BookLoan loan){
        loanMap.put(loan.getId(), loan);
    }

    public Collection<BookLoan> getAllLoans(){
        return loanMap.values();
    }

    public BookLoan getLoan(String id){
        return loanMap.get(id);
    }

    public boolean doesLoanExist(String id) {
        return loanMap.containsKey(id);
    }

    public String getLender(Book book) {
        String lender = getAllLoans().stream()
                .filter(loan -> loan.getStatus().equals(LoanStatus.LOANED_OUT))
                .filter(loan -> loan.getIsbn().equals(book.getIsbn()))
                .map(loan ->personRepository.getPersonById(loan.getMember()).getFullName())
                .findFirst().orElse("this book is not loaned out.");
        return lender;
    }
}
