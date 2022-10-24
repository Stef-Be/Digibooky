package com.switchfully.duckbusters.digibooky.service;


import com.switchfully.duckbusters.digibooky.api.AddLoanDTO;
import com.switchfully.duckbusters.digibooky.api.LoanMapper;
import com.switchfully.duckbusters.digibooky.domain.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.LoanStatus;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.springframework.stereotype.Component;

import static com.switchfully.duckbusters.digibooky.domain.LoanStatus.*;

@Component
public class LoanService {

    private final LoanRepository loanRepo;
    private final LoanMapper loanMapper;

    private final PersonRepository personRepo;

    private final BookRepository bookRepo;

    public LoanService(LoanRepository loanRepo, LoanMapper loanMapper, PersonRepository personRepo, BookRepository bookRepository) {
        this.loanRepo = loanRepo;
        this.loanMapper = loanMapper;
        this.personRepo = personRepo;
        this.bookRepo = bookRepository;
    }

    public void LoanBook(AddLoanDTO loan){
        validateLoan(loan);
        loanRepo.addLoan(loanMapper.createNewLoan(loan));

    }

    public void validateLoan(AddLoanDTO freshLoan){
        validateLoanMember(freshLoan.getMemberId());
        validateLoanBook(freshLoan.getIsbn());
        loanRepo.getAllLoans().stream()
                .filter(loan -> loan.getStatus().equals(LOANED_OUT))
                .forEach(loan -> checkIfLoanedOut(freshLoan,loan));
    }

    public void checkIfLoanedOut(AddLoanDTO loan, BookLoan existing){
        if (loan.getIsbn().equals(existing.getBook())) throw new IllegalArgumentException("this book is currently loaned out!");
    }

    public void validateLoanMember(String loanMember){
        if(loanMember == null) throw new IllegalArgumentException("no member provided!");
        if(!personRepo.doesPersonExist(loanMember)) throw new IllegalArgumentException("no such member exists!");
    }

    public void validateLoanBook(String loanISBN){
        if(loanISBN == null) throw new IllegalArgumentException("no member provided!");
        if(!bookRepo.doesBookExist(loanISBN)) throw new IllegalArgumentException("no such book exists!");
    }
}
