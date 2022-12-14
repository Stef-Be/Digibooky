package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.UpdateBookDTO;
import com.switchfully.duckbusters.digibooky.api.mapper.BookMapper;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.switchfully.duckbusters.digibooky.domain.person.Feature.CRUD_BOOK;
import static com.switchfully.duckbusters.digibooky.domain.person.Feature.SEE_BORROWERS;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final SecurityService securityService;
    private final ValidationService validationService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, SecurityService securityService, ValidationService validationService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.securityService = securityService;
        this.validationService = validationService;
    }

    public List<AllBookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .filter(Book::isInCatalogue)
                .map(bookMapper::mapToAllBookDto)
                .collect(Collectors.toList());
    }

    public List<SingleBookDto> getByIsbn(String isbn) {
        List<Book> foundBooks = bookRepository.getByIsbn(isbn);
        return foundBooks.stream()
                .filter(Book::isInCatalogue)
                .map(bookMapper::mapToSingleBookDtoWithOutLender)
                .collect(Collectors.toList());
    }

    public void registerNewBook(String authorization, RegisterBookDTO freshBook) {
        securityService.validateAuthorization(authorization, CRUD_BOOK);
        validationService.validateIsbn(freshBook.getIsbn());
        validationService.assertNotNullOrBlank(freshBook.getTitle(), "Title");
        validationService.assertNotNullOrBlank(freshBook.getAuthorLastName(), "Last name");
        bookRepository.addNewBook(bookMapper.createBookFromDto(freshBook));
    }


    public void softDeleteBook(String auths, String isbn) {
        securityService.validateAuthorization(auths, CRUD_BOOK);
        bookRepository.getExactBookByIsbn(isbn).setInCatalogue(false);
    }


    public void updateBook(String auths, String isbn, UpdateBookDTO update) {
        securityService.validateAuthorization(auths, CRUD_BOOK);
        bookMapper.updateBookFromDTO(update, bookRepository.getExactBookByIsbn(isbn));
    }


    public List<SingleBookDto> getByTitle(String title) {
        List<Book> foundBooks = bookRepository.getByTitle(title);
        return foundBooks.stream()
                .filter(Book::isInCatalogue)
                .map(bookMapper::mapToSingleBookDtoWithOutLender)
                .collect(Collectors.toList());
    }

    public List<SingleBookDto> getByAuthor(String firstName, String lastName) {
        List<Book> foundBooks = bookRepository.getByAuthor(firstName, lastName);
        return foundBooks.stream()
                .filter(Book::isInCatalogue)
                .map(bookMapper::mapToSingleBookDtoWithOutLender)
                .collect(Collectors.toList());
    }

    public SingleBookDto getExactBookByIsbn(String authorization, String isbn) {
        if(authorization==null||authorization.equals("Basic Og==") ){
            if (!bookRepository.getExactBookByIsbn(isbn).isInCatalogue()) {
                throw new IllegalArgumentException("This book is no longer available.");
            }
            return bookMapper.mapToSingleBookDtoWithOutLender(bookRepository.getExactBookByIsbn(isbn));
        }

        securityService.validateAuthorization(authorization, SEE_BORROWERS);
        if (!bookRepository.getExactBookByIsbn(isbn).isInCatalogue()) {
            throw new IllegalArgumentException("This book is no longer available.");
        }
        return bookMapper.mapToSingleBookDto(bookRepository.getExactBookByIsbn(isbn));
    }

    public void restoreBook(String authorization, String isbn) {
        securityService.validateAuthorization(authorization, CRUD_BOOK);
        bookRepository.getExactBookByIsbn(isbn).setInCatalogue(true);
    }
}