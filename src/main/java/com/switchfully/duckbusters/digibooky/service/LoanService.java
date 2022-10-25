package com.switchfully.duckbusters.digibooky.service;


import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.api.mapper.LoanMapper;
import com.switchfully.duckbusters.digibooky.api.dto.returnBookDTO;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static com.switchfully.duckbusters.digibooky.domain.loan.LoanStatus.*;
import static com.switchfully.duckbusters.digibooky.domain.person.Feature.*;

@Component
public class LoanService {

    private final LoanRepository loanRepo;
    private final LoanMapper loanMapper;

    private final PersonRepository personRepo;

    private final BookRepository bookRepo;

    private final ValidationService validation;

    public LoanService(LoanRepository loanRepo, LoanMapper loanMapper, PersonRepository personRepo, BookRepository bookRepository, ValidationService validation) {
        this.loanRepo = loanRepo;
        this.loanMapper = loanMapper;
        this.personRepo = personRepo;
        this.bookRepo = bookRepository;
        this.validation = validation;
    }

    public void loanBook(String auths, String isbn) {
        validation.validateAuthorization(auths, LOAN_BOOK);
        validateLoan(isbn);
        loanRepo.addLoan(new BookLoan(personRepo.getPersonbyEmail(validation.getEmail(auths)).getId(), isbn));

    }

    public void validateLoan(String isbn) {
        validateLoanBook(isbn);
        loanRepo.getAllLoans().stream()
                .filter(loan -> loan.getStatus().equals(LOANED_OUT))
                .forEach(loan -> checkIfLoanedOut(isbn, loan));
    }

    public void checkIfLoanedOut(String isbn, BookLoan existing) {
        if (isbn.equals(existing.getIsbn()))
            throw new IllegalArgumentException("this book is currently loaned out!");
    }

    public void validateLoanMember(String loanMember) {
        if (loanMember == null) throw new IllegalArgumentException("no member provided!");
        if (!personRepo.doesPersonExist(loanMember)) throw new IllegalArgumentException("no such member exists!");
    }

    public void validateLoanBook(String loanISBN) {
        if (loanISBN == null) throw new IllegalArgumentException("no book provided!");
        if (!bookRepo.doesBookExist(loanISBN)) throw new IllegalArgumentException("no such book exists!");
        if (!bookRepo.getExactBookByIsbn(loanISBN).isInCatalogue())throw new IllegalArgumentException("This book is no longer available!") ;
    }

    public void validateReturnBook(String loanId) {
        if (!loanRepo.doesLoanExist(loanId)) throw new IllegalArgumentException("No such loan exists!");
        if (loanRepo.getLoan(loanId).getStatus().equals(RETURNED))
            throw new IllegalArgumentException("This book has already been returned!");
    }

    public returnBookDTO returnBook(String auths, String loanId) {
        validation.validateAuthorization(auths, LOAN_BOOK);
        validateReturnBook(loanId);
        BookLoan loan = loanRepo.getLoan(loanId);
        loan.setStatus(RETURNED);
        return new returnBookDTO(loanId, loan.getDueDate(), checkDueDate(loan));
    }

    public String checkDueDate(BookLoan loan) {
        LocalDate dueDate = loan.getDueDate();
        if (LocalDate.now().isBefore(dueDate.plusDays(1))) return "This book has been returned on time!";
        return "This book is " + Duration.between(dueDate, LocalDate.now()) + " days late!";
    }

    public List<LoanDto> getLoansFromMember(String auths, String memberId){
        validation.validateAuthorization(auths, VIEW_LOANS);
        validateLoanMember(memberId);
        return loanRepo.getAllLoans().stream()
                .filter(bookLoan -> bookLoan.getMember().equals(memberId))
                .map(loanMapper::mapLoanToDTO).toList();
    }

    public List<LoanDto> getOverdueLoans(String librarianId){
        validation.validateAuthorization(librarianId, VIEW_LOANS);
        return loanRepo.getAllLoans().stream()
                .filter(bookLoan -> bookLoan.getStatus().equals(LOANED_OUT))
                .filter(bookLoan -> bookLoan.getDueDate().isBefore(LocalDate.now()))
                .map(loanMapper::mapLoanToDTO).toList();
    }
}
