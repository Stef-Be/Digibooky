package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.UpdateBookDTO;
import com.switchfully.duckbusters.digibooky.api.mapper.AuthorMapper;
import com.switchfully.duckbusters.digibooky.api.mapper.BookMapper;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.duckbusters.digibooky.domain.person.Feature.CRUD_BOOK;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    private final ValidationService validation;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, ValidationService validation, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.validation = validation;
        this.authorMapper = authorMapper;
    }

    public List<AllBookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .filter(Book::isInCatalogue)
                .map(bookMapper::mapToAllBookDto)
                .collect(Collectors.toList());
    }

    public List<SingleBookDto> getByIsbn(String isbn) {
        List<Book> foundBooks = bookRepository.getByIsbn(isbn);
        return foundBooks.stream().filter(Book::isInCatalogue).map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }

    public void registerNewBook(String librarianId, RegisterBookDTO freshBook) {
        validation.validateAuthorization(librarianId, CRUD_BOOK);
        validateIsbn(freshBook.getIsbn());
        validateTitle(freshBook.getTitle());
        validateLastName(freshBook.getAuthorLastName());
        bookRepository.addNewBook(bookMapper.createBookFromDto(freshBook));
    }

    private void validateIsbn(String isbn) {
        if (isbn == null) throw new IllegalArgumentException("isbn is empty!");
        if (isbn.length() != 13) throw new IllegalArgumentException("isbn must be 13 characters long!");
        if (bookRepository.doesBookExist(isbn)) checkReRegisterBook(isbn);
    }

    private void checkReRegisterBook(String isbn) {
        if (bookRepository.getExactBookByIsbn(isbn).isInCatalogue())
            throw new IllegalArgumentException("isbn must be unique!");

        bookRepository.getExactBookByIsbn(isbn).setInCatalogue(true);
    }

    private void validateTitle(String title) {
        if (title == null) throw new IllegalArgumentException("title is empty!");
    }

    private void validateLastName(String name) {
        if (name == null) throw new IllegalArgumentException("author last name is empty!");
    }

    public void softDeleteBook(String librarianId, String isbn) {
        validation.validateAuthorization(librarianId, CRUD_BOOK);
        bookRepository.getExactBookByIsbn(isbn).setInCatalogue(false);
    }


    public void updateBook(String librarianId, String isbn, UpdateBookDTO update){
        validation.validateAuthorization(librarianId, CRUD_BOOK);
        bookMapper.updateBookFromDTO(update,bookRepository.getExactBookByIsbn(isbn));
    }


    public List<SingleBookDto> getByTitle(String title) {
        List<Book> foundBooks = bookRepository.getByTitle(title);
        return foundBooks.stream().filter(Book::isInCatalogue).map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }

    public List<SingleBookDto> getByAuthor(String firstName, String lastName) {
        List<Book> foundBooks = bookRepository.getByAuthor(firstName, lastName);
        return foundBooks.stream().filter(Book::isInCatalogue).map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }

    public SingleBookDto getExactBookByIsbn(String isbn) {
        return bookMapper.mapToSingleBookDto(bookRepository.getExactBookByIsbn(isbn));
    }
}