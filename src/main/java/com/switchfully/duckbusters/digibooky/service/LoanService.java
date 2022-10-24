package com.switchfully.duckbusters.digibooky.service;


import com.switchfully.duckbusters.digibooky.api.dto.AddLoanDTO;
import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.api.mapper.LoanMapper;
import com.switchfully.duckbusters.digibooky.api.dto.returnBookDTO;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.person.Feature;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public void LoanBook(AddLoanDTO loan) {
        validateLoan(loan);
        loanRepo.addLoan(loanMapper.createNewLoan(loan));

    }

    public void validateLoan(AddLoanDTO freshLoan) {
        validateLoanMember(freshLoan.getMemberId());
        validateLoanBook(freshLoan.getIsbn());
        loanRepo.getAllLoans().stream()
                .filter(loan -> loan.getStatus().equals(LOANED_OUT))
                .forEach(loan -> checkIfLoanedOut(freshLoan, loan));
    }

    public void checkIfLoanedOut(AddLoanDTO loan, BookLoan existing) {
        if (loan.getIsbn().equals(existing.getBook()))
            throw new IllegalArgumentException("this book is currently loaned out!");
    }

    public void validateLoanMember(String loanMember) {
        if (loanMember == null) throw new IllegalArgumentException("no member provided!");
        if (!personRepo.doesPersonExist(loanMember)) throw new IllegalArgumentException("no such member exists!");
    }

    public void validateLoanBook(String loanISBN) {
        if (loanISBN == null) throw new IllegalArgumentException("no book provided!");
        if (!bookRepo.doesBookExist(loanISBN)) throw new IllegalArgumentException("no such book exists!");
    }

    public void validateReturnBook(String id) {
        if (!loanRepo.doesLoanExist(id)) throw new IllegalArgumentException("No such loan exists!");
        if (loanRepo.getLoan(id).getStatus().equals(RETURNED))
            throw new IllegalArgumentException("This book has already been returned!");
    }

    public returnBookDTO returnBook(String id) {
        validateReturnBook(id);
        BookLoan loan = loanRepo.getLoan(id);
        loan.setStatus(RETURNED);
        return new returnBookDTO(id, loan.getDueDate(), checkDueDate(loan));
    }

    public String checkDueDate(BookLoan loan) {
        LocalDate dueDate = loan.getDueDate();
        if (LocalDate.now().isBefore(dueDate.plusDays(1))) return "This book has been returned on time!";
        return "This book is " + Duration.between(dueDate, LocalDate.now()) + " days late!";
    }

    public List<LoanDto> getLoansFromMember(String librarianId, String memberId){
        validation.validateAuthorization(librarianId, VIEW_MEMBER_LOANS);
        validateLoanMember(memberId);
        return loanRepo.getAllLoans().stream()
                .filter(bookLoan -> bookLoan.getMember().equals(memberId))
                .map(loanMapper::mapLoanToDTO).toList();
    }
}
