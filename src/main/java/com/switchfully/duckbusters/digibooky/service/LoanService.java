package com.switchfully.duckbusters.digibooky.service;


import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.api.dto.LoanReceipt;
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

    private final SecurityService securityService;
    private final ValidationService validationService;

    public LoanService(LoanRepository loanRepo, LoanMapper loanMapper, PersonRepository personRepo, SecurityService securityService, ValidationService validationService) {
        this.loanRepo = loanRepo;
        this.loanMapper = loanMapper;
        this.personRepo = personRepo;
        this.securityService = securityService;
        this.validationService = validationService;
    }

    public LoanReceipt loanBook(String auths, String isbn) {
        securityService.validateAuthorization(auths, LOAN_BOOK);
        validationService.validateLoan(isbn);
        BookLoan loan = new BookLoan(personRepo.getPersonbyEmail(securityService.getEmail(auths)).getId(), isbn);
        loanRepo.addLoan(loan);

        return new LoanReceipt(loan.getId(), loan.getIsbn(), loan.getDueDate());
    }

    public returnBookDTO returnBook(String auths, String loanId) {
        securityService.validateAuthorization(auths, LOAN_BOOK);
        validationService.validateReturnBook(loanId);
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
        securityService.validateAuthorization(auths, VIEW_LOANS);
        validationService.validateLoanMember(memberId);
        return loanRepo.getAllLoans().stream()
                .filter(bookLoan -> bookLoan.getMember().equals(memberId))
                .map(loanMapper::mapLoanToDTO).toList();
    }

    public List<LoanDto> getOverdueLoans(String librarianId){
        securityService.validateAuthorization(librarianId, VIEW_LOANS);
        return loanRepo.getAllLoans().stream()
                .filter(bookLoan -> bookLoan.getStatus().equals(LOANED_OUT))
                .filter(bookLoan -> bookLoan.getDueDate().isBefore(LocalDate.now()))
                .map(loanMapper::mapLoanToDTO).toList();
    }
}
