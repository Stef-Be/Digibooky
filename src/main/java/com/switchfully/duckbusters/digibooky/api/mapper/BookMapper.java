package com.switchfully.duckbusters.digibooky.api.mapper;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.api.dto.UpdateBookDTO;
import com.switchfully.duckbusters.digibooky.domain.book.Author;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private LoanRepository loanRepository;
    private PersonRepository personRepository;

    public BookMapper(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public AllBookDTO mapToAllBookDto(Book book) {
        return new AllBookDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor().getFullName());
    }

    public SingleBookDto mapToSingleBookDto(Book book) {

        String lender = loanRepository.getLender(book);

        return new SingleBookDto().setLender(lender)
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor().getFullName())
                .setSummary(book.getSummary());
    }

    public Book createBookFromDto(RegisterBookDTO freshBook) {
        Author author = new Author(freshBook.getAuthorFirstName(), freshBook.getAuthorLastName());
        return new Book(freshBook.getIsbn(), freshBook.getTitle(), author, freshBook.getSummary());
    }

    public void updateBookFromDTO(UpdateBookDTO update, Book book) {

        if (update.getTitle() != null && !update.getTitle().isBlank()) book.setTitle(update.getTitle());

        if (update.getAuthorFirstName() != null && !update.getAuthorFirstName().isBlank())
            book.getAuthor().setAuthorFirstname(update.getAuthorFirstName());

        if (update.getAuthorLastName() != null && !update.getAuthorLastName().isBlank())
            book.getAuthor().setAuthorLastname(update.getAuthorLastName());

        if (update.getSummary() != null && !update.getSummary().isBlank()) book.setSummary(update.getSummary());
    }
}


