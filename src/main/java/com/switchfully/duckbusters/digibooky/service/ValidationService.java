package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;

import static com.switchfully.duckbusters.digibooky.domain.loan.LoanStatus.LOANED_OUT;
import static com.switchfully.duckbusters.digibooky.domain.loan.LoanStatus.RETURNED;

@Service
public class ValidationService {
private final BookRepository bookRepository;
private final PersonRepository personRepository;

private final LoanRepository loanRepository;

    public ValidationService(BookRepository bookRepository, PersonRepository personRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.loanRepository = loanRepository;
    }

    public void validateIsbn(String isbn) {
        if (isbn == null) throw new IllegalArgumentException("isbn is empty!");
        if (isbn.length() != 13) throw new IllegalArgumentException("isbn must be 13 characters long!");
        if (bookRepository.doesBookExist(isbn)) checkReRegisterBook(isbn);
    }

    private void checkReRegisterBook(String isbn) {
        if (bookRepository.getExactBookByIsbn(isbn).isInCatalogue())
            throw new IllegalArgumentException("isbn must be unique!");
        bookRepository.getExactBookByIsbn(isbn).setInCatalogue(true);
    }

    public void validateLoan(String isbn) {
        validateLoanBook(isbn);
        loanRepository.getAllLoans().stream()
                .filter(loan -> loan.getStatus().equals(LOANED_OUT))
                .forEach(loan -> checkIfLoanedOut(isbn, loan));
    }

    public void validateLoanBook(String loanISBN) {
        if (loanISBN == null) throw new IllegalArgumentException("no book provided!");
        if (!bookRepository.doesBookExist(loanISBN)) throw new IllegalArgumentException("no such book exists!");
        if (!bookRepository.getExactBookByIsbn(loanISBN).isInCatalogue())throw new IllegalArgumentException("This book is no longer available!") ;
    }

    public void checkIfLoanedOut(String isbn, BookLoan existing) {
        if (isbn.equals(existing.getIsbn()))
            throw new IllegalArgumentException("this book is currently loaned out!");
    }

    public void validateReturnBook(String loanId) {
        if (!loanRepository.doesLoanExist(loanId)) throw new IllegalArgumentException("No such loan exists!");
        if (loanRepository.getLoan(loanId).getStatus().equals(RETURNED))
            throw new IllegalArgumentException("This book has already been returned!");
    }

    public void validateLoanMember(String loanMember) {
        if (loanMember == null) throw new IllegalArgumentException("no member provided!");
        if (!personRepository.doesPersonExist(loanMember)) throw new IllegalArgumentException("no such member exists!");
    }

    void assertNotNull(String value, String field) {
        if (value == null) throw new IllegalArgumentException(field + " can not be empty!");
    }

    void validateThatPerson(CreatePersonDTO freshPerson, Person existing) {
        if (freshPerson.geteMail().equals(existing.geteMail()))
            throw new IllegalArgumentException("E mail is not unique!");
        if (freshPerson.getInss().equals(existing.getInss())) throw new IllegalArgumentException("inss is not unique!");
    }

    void validateEmail(String eMail) {
        if (eMail == null || !eMail.matches("^[A-z0-9]*@[A-z0-9]*\\.[A-z0-9]*$"))
            throw new IllegalArgumentException("E mail does not conform to format!");
    }
}
